package sundeckunical.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientManager implements Runnable {

	private BufferedReader br;

	private String name;

	private PrintWriter pw;

	private final ServerGameManager serverGameManager;

	private final Socket socket;

	public ClientManager(final Socket socket, final ServerGameManager server) {
		this.socket = socket;
		this.serverGameManager = server;
	}

	public void dispatch(final String message) {
		if (pw != null && message != null) {
			pw.println(message);
		}
	}

	public String getName() {
		return name;

	}

	@Override
	public void run() {
		try {
			serverGameManager.setReady(this);
			final boolean running = true;
			while (running) {
				final String buffer = br.readLine();
					serverGameManager.received(buffer);
			}
		} catch (final IOException e) {
			System.out.println("Client disconnected: " + name);
		}
	}

//	String setup() throws IOException {
//		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		pw = new PrintWriter(socket.getOutputStream(), true);
//		name = br.readLine();
//		server.dispatch(server.getConnectedClientNames(), null);
//		return name;
//	}
	

	public void setup() throws IOException {
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(socket.getOutputStream(), true);
		name = br.readLine();
		serverGameManager.dispatch(serverGameManager.getConnectedClientNames(), null);
	}

}
