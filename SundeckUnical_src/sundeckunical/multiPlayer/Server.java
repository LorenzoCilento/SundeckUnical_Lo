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
		while(contNumberPlayer != numberPlayer){
			try {
				System.out.println("Start server");
				System.out.println("server player "+contNumberPlayer );
				Socket socket = server.accept();
				System.out.println("dopo accept");
				ClientManager clientManager = new ClientManager(this,socket);
				clients.add(clientManager);
				contNumberPlayer++;
				System.out.println("dopo add");
//				clientManager.startConnection();
				System.out.println("dopo startConnection");
		 		System.out.println("server player "+contNumberPlayer );
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("uscito da run");
		System.out.println("numero clients aggiunti "+clients.size());
		for(ClientManager c : clients)
			try {
				c.startConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
