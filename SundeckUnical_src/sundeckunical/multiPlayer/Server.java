package sundeckunical.multiPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class Server extends Thread {
	private Collection<ClientManager> clients = new ArrayList<ClientManager>();
	private int port;
	private int numberPlayer;
	private ServerSocket server;
	
	public Server(int port, int numberPlayer) throws IOException {
		this.port = port;
		this.numberPlayer = numberPlayer;
		this.server = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		int contNumberPlayer = 0;
//		try {
//			socket = new Socket(server.getInetAddress(),port);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		while(contNumberPlayer != numberPlayer){
			try {
				System.out.println("server player "+contNumberPlayer );
				Socket socket = server.accept();
				ClientManager clientManager = new ClientManager(this,socket);
				clients.add(clientManager);
				clientManager.startConnection();
				System.out.println("server player "+contNumberPlayer );
				contNumberPlayer++;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		for (ClientManager c : clients)
			try {
				c.sendStart();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public Collection<ClientManager> getClients() {
		return clients;
	}
	
	public int getPort(){
		return port;
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server(8080, 2);
		server.start();
	}

}
