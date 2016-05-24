package sundekunical.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		int port = 2727;
		try {
			port = Integer.parseInt(args[0]);
		} catch (final Exception e) {
			System.out
					.println("Insert port number as command line parameter. Using default: 2727");
		}
		final Server server;
		server = new Server(port);
		server.runServer();
	}

	private final int port;

	private final boolean running = true;

	private ServerSocket serverSocket;

	public Server(final int port) {
		this.port = port;
	}

	private void runServer() throws IOException {
		serverSocket = new ServerSocket(port);
		while (running) {
			System.out.println("waiting for connection");
			final ServerGameManager gameManager = new ServerGameManager();
			final Socket socket1 = serverSocket.accept();
			final ClientManager cm1 = new ClientManager(socket1, gameManager);
			gameManager.add(cm1);
			System.out.println("client 1 connected");
			final Socket socket2 = serverSocket.accept();
			final ClientManager cm2 = new ClientManager(socket2, gameManager);
			gameManager.add(cm2);
			System.out.println("client 2 connected");
			gameManager.startGame();
			System.out.println("game created");
		}
	}

}
