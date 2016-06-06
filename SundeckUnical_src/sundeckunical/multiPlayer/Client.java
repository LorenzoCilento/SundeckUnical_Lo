package sundeckunical.multiPlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import sundeckunical.core.GameManager;
import sundeckunical.gui.MainFrame;

public class Client extends Thread {
	private Socket socket;
	private GameManager gameManager;
	private BufferedReader input;
	private DataOutputStream output;
	private String name;
	private MainFrame mainFrame;

	public Client(Socket socket, String name, MainFrame mainFrame) throws IOException {
		this.name = name;
		this.gameManager = new GameManager();
		this.gameManager.setClient(this);
		this.gameManager.loadWorld();
		this.gameManager.setupObject(name);
		System.out.println("Costruttore di CLIENT");
		this.socket = socket;
		this.mainFrame = mainFrame;
		input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		output = new DataOutputStream(socket.getOutputStream());
		startConnection();
	}

	public String getClientName() {
		return name;
	}

	public void startConnection() throws IOException {
		System.out.println("Start connection CLIENT");
		output.writeBytes("Connect me" + "\n");
		if (input.readLine().equals("yes parse your status")) {
			System.out.println("client: yes parse your status ");
			output.writeBytes(gameManager.statusPlayerToString() + "\n");
		}
		this.start();
	}

	public void notifyAddNewPlayer() throws IOException {
		System.out.println("client: " + "new player");
		output.writeBytes("send status" + "\n");
		System.out.println("dopo send status");
		String line = input.readLine();
		System.out.println(line);
		gameManager.addPlayer(line);
	}

	public void notifyMyState() throws IOException {
		output.writeBytes("myState-" + gameManager.statusPlayerToString() + "\n");
	}

	public void notifyMyRealState() throws IOException {
		String status = gameManager.statusPlayerToString();
		output.writeBytes("send state?" + "\n");
		if (input.readLine().equals("yes")) {
			System.out.println("Client: " + name + " Notify my real status " + status);
			output.writeBytes(status + "\n");
		}
	}

	public void receivedState() throws IOException {
//		output.writeBytes("state" + "\n");
		String status = input.readLine();
		System.out.println("Client " + name + " received state: " + status);
		gameManager.refreshPlayer(status);
	}

	public void sendMyStatus() throws IOException {

	}

	public void addPrecPlayer() throws IOException {
		output.writeBytes("send" + "\n");
		String state = input.readLine();
		System.out.println("sto aggiungengo a gamemanager di " + gameManager.getWorld().getPlayerName() + " " + state);
		gameManager.addPlayer(state);
	}

	public void sendStatus() throws IOException {
		String state = gameManager.statusPlayerToString();
		output.writeBytes(state + "\n");
	}

	@Override
	public void run() {
		System.out.println("CLIENT RUN");
		boolean isStop = true;
		boolean isStart = false;
		try {
			while (isStop) {
				System.out.println("CLIENT RUN isStop");
				String line = input.readLine();
				System.out.println(line);
				if (line.equals("Add new player")) {
					System.out.println("run add new player");
					notifyAddNewPlayer();
				}
				if (line.equals("please send your status")) {
					System.out.println("please send");
					sendStatus();
				}
				if (line.equals("Add Prec Player")) {
					addPrecPlayer();
				}
				if (line.equals("Start")) {
					System.out.println("CLIENT RUN if Start");
					isStop = false;
					isStart = true;
					gameManager.setRunning(true);
					output.writeBytes("Start" + "\n");
				}
			}
			// TODO start multiplayer (gamePanel.start())
			mainFrame.startGameNetwork();
			mainFrame.getGamePanel().startNetwork(gameManager);
			while (isStart) {
				System.out.println("CLIENT RUN start");
				String line = input.readLine();
				if (line.equals("myState")) {
					System.out.println("Client my state");
					notifyMyRealState();
				}
				if (line.equals("state")) {
					System.out.println("entro received state client");
					receivedState();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
