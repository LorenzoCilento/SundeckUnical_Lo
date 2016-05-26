package sundeckunical.multiPlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import sundeckunical.core.GameManager;

public class Client extends Thread {
	private Socket socket;
	private GameManager gameManager;
	private BufferedReader input;
	private DataOutputStream output;
	private String name;

	public Client(Socket socket, String name, GameManager gameManager) throws IOException {
		this.name = name;
		this.gameManager = gameManager;
		this.gameManager.setClient(this);
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		output = new DataOutputStream(socket.getOutputStream());
		startConnection();
	}

	public String getClientName() {
		return name;
	}

	public void startConnection() throws IOException {
		output.writeBytes("Connect me" + "\n");
		if (input.readLine().equals("yes parse your status")) {
			output.writeBytes(gameManager.statusPlayerToString() + "\n");
		}
		this.start();
	}

	public void notifyAddNewPlayer() throws IOException {
		output.writeBytes("send status" + "\n");
		String line = input.readLine();
		gameManager.addPlayer(line);
	}
	
	public void notifyMyState() throws IOException{
		output.writeBytes("myState"+"\n");
	}
	
	public void notifyMyRealState() throws IOException{
		String status = gameManager.statusPlayerToString();
		output.writeBytes(status+"\n");
	}
	
	public void receivedState() throws IOException{
		output.writeBytes("state"+"\n");
	}

	@Override
	public void run() {
		boolean isStop = true;
		boolean isStart = false;
		try {
			while (isStop) {
				String line = input.readLine();
				if (line.equals("Add new player"))
					notifyAddNewPlayer();
				if (line.equals("Start")) {
					isStop = false;
					isStart = true;
				}
			}
			// TODO start multiplayer (gamePanel.start())
			output.writeBytes("Start" + "\n");
			while (isStart) {
				String line = input.readLine();
				if(line.equals("myState")){
					notifyMyRealState();
				}
				if(line.equals("state")){
					receivedState();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
