package sundeckunical.multiPlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientManager extends Thread {
	private Server server;
	private Socket socket;
	private BufferedReader input;
	private DataOutputStream output;
	private String statusPlayer;
	
	public ClientManager(Server server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		output = new DataOutputStream(socket.getOutputStream());
	}

	public void startConnection() throws IOException {
		System.out.println("Start connection CLIENT MANAGER");
		if (input.readLine().equals("Connect me")) {
			System.out.println("if connect me");
			output.writeBytes("yes parse your status" + "\n");
		}
		System.out.println("dopo if");
		String state = input.readLine();
		System.out.println("state: " + state);
		System.out.println("passo");
		for (ClientManager c : server.getClients()) {
			System.out.println("for server");
			if (c != this)
				c.notifyAddNewPlayer(state);
			else {
				if (server.getClients().size() > 1)
					c.sendYourStatus();
			}
		}
	}

	public void sendYourStatus() throws IOException {
		String state = null;
		for (ClientManager c : server.getClients()) {
			if (c != this)
				state = c.sendStatus();
			break;
		}
		for (ClientManager c : server.getClients())
			if (c == this) {
				output.writeBytes("Add Prec Player" + "\n");
				if (input.readLine().equals("send"))
					output.writeBytes(state + "\n");
			}

	}

	public String sendStatus() throws IOException {
		output.writeBytes("please send your status" + "\n");
		String state = input.readLine();
		System.out.println("state prec player " + state);
		return state;
	}

	public void notifyAddNewPlayer(String state) throws IOException {
		output.writeBytes("Add new player" + "\n");
		System.out.println("server: " + "new player");
		// String line = input.readLine();
		// System.out.println("server:" + line);
		if (input.readLine().equals("send status")) {
			System.out.println("server dentro if send status");
			output.writeBytes(state + "\n");
		}
	}

	public void sendStart() throws IOException {
		output.writeBytes("Start" + "\n");
	}

	public void notifyMyState(String line) throws IOException {
//		System.out.println("server my state chiede stato");
//		output.writeBytes("myState" + "\n");
//		if (input.readLine().equals("send state?"))
//			output.writeBytes("yes" + "\n");
//		output.writeBytes("state"+"\n");
		System.out.println("STATO RICEVUTO DAL SERVER: " + line);
		for (ClientManager c : server.getClients()) {
			if (c != this)
				c.receivedState(line);
		}

	}

	public void receivedState(String status) throws IOException {
		this.statusPlayer = status;
		output.writeBytes("state" + "\n");
		output.writeBytes(status + "\n");
		System.out.println("Server: received state" + status);
	}

	public void sendState() throws IOException {
		System.out.println("Server: send state" + this.statusPlayer);
		output.writeBytes(this.statusPlayer + "\n");
		
	}

	@Override
	public void run() {
		System.out.println("CLIENT MANAGER RUN");
		boolean isStart = false;
		try {
			String line = input.readLine();
			System.out.println("line: " + line);
			if (line.equals("Start")) {
				System.out.println("CLIENT MANAGER Start");
				isStart = true;
			}
			while (isStart) {
				// System.out.println("CLIENT MANAGER RUN is start");
				line = input.readLine();
				String[] split = line.split("-");
				if (split[0].equals("myState")) {
					System.out.println("Server: mystate");
					notifyMyState(split[1].toString());
				}
				if (line.equals("state")) {
					sendState();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
