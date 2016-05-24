package sundeckunical.gui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import sundeckunical.core.GameManager;

public class NetworkManager implements Runnable {

	private final MainFrame mainFrame;

	private final String name;

	private List<String> playerNames;

	private PrintWriter pw;

	private final Socket socket;
	
	GameManager gameManager;

	public NetworkManager(final Socket socket, final String name,
			final MainFrame mainFrame) {
		this.socket = socket;
		this.name = name;
		this.mainFrame = mainFrame;

	}

	public void close() {
		try {
			socket.close();
		} catch (final IOException e) {
			// do nothing
		}
	}

	public void dispatch(final String message) {
		pw.println(message);
		System.out.println("dispatch NM " + message);
	}

	public List<String> getAllPlayerNames() {
		return playerNames;
	}

	public String getPlayerName() {
		return name;
	}

	@Override
	public void run() {
		try {
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(getPlayerName());
			String buffer = br.readLine();
			while (!buffer.equals("#START")) {
				final String[] split = buffer.split(";");
				if (split.length != 0) {
					playerNames = new ArrayList<>();
					for (final String name : split) {
						playerNames.add(name);
					}
				}
				buffer = br.readLine();
			}
			final GameManager gameManager = mainFrame.startNetworkGame(this);
			this.gameManager = gameManager; 
			buffer = br.readLine();
			while (buffer != null) {
				// System.out.println(buffer);
				// if (buffer.equals("LOSE")) {
				// JOptionPane.showMessageDialog(mainFrame, "You Won!");
				// buffer = null;
				// socket.close();
				// mainFrame.showMenu();
				// } else if (buffer.equals("WIN")) {
				// JOptionPane.showMessageDialog(mainFrame, "You Lose!");
				// buffer = null;
				// socket.close();
				// mainFrame.showMenu();
				// } else {
				gameManager.parseStatusFromString(buffer,this);
				mainFrame.getGamePanel().repaint();
				buffer = br.readLine();
				// }
			}
		} catch (final IOException e) {
			System.out.println("Connection closed");
		}
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
}
