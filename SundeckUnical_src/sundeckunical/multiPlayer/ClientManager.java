package sundeckunical.multiPlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import sundeckunical.gui.MainFrame;

public class ClientManager extends Thread {
	private Server server;
	private Socket socket;
	private BufferedReader input;
	private DataOutputStream output;
	private String line;

	public ClientManager(Server server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		output = new DataOutputStream(socket.getOutputStream());
	}

	public void startConnection() throws IOException {
		if (input.readLine().equals("Connect me"))
			output.writeBytes("yes parse your status" + "\n");
		String state = input.readLine();
		for (ClientManager c : server.getClients()) {
			if (c != this)
				c.notifyAddNewPlayer(state);
		}
		this.start();
	}

	public void notifyAddNewPlayer(String state) throws IOException {
		// TODO fare comunicazione
		output.writeBytes("Add new player" + "\n");
		String line = input.readLine();
		if (line.equals("send status"))
			output.writeBytes(state + "\n");
	}

	public void sendStart() throws IOException {
		output.writeBytes("Start" + "\n");
	}
	
	public void notifyMyState() throws IOException{
		output.writeBytes("myState"+"\n");	
		String line = input.readLine();
		for(ClientManager c : server.getClients()){
			c.receivedState(line);
		}
			
	}
	
	public void receivedState(String status) throws IOException{
		output.writeBytes("state"+"\n");
		this.line = status;
	}
	
	public void sendState() throws IOException{
		output.writeBytes(this.line+"\n");
	}

	@Override
	public void run() {
		boolean isStart = false;
		try {
			String line = input.readLine();
			if(line.equals("Start"))
				isStart = true;
			while(isStart){
				line = input.readLine();
				if(line.equals("myState")){
					notifyMyState();
				}
				if(line.equals("state")){
					sendState();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
