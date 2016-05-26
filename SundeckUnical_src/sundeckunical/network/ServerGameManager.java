package sundeckunical.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sundeckunical.core.Direction;
import sundeckunical.core.GameManager;

class ServerGameManager implements Runnable{
	private final Set<ClientManager> clients = new HashSet<ClientManager>();

	GameManager gameManager;
	private final Set<ClientManager> readyClients = new HashSet<ClientManager>();
	
	boolean running;

	public void add(final ClientManager cm) {
		clients.add(cm);
	}

	public void dispatch(final String message,
			final ClientManager senderClientManager) {
		for (final ClientManager cm : clients) {
			if (cm != senderClientManager) {
				cm.dispatch(message);
			}
		}
	}

	public String getConnectedClientNames() {
		final StringBuilder sb = new StringBuilder();
		for (final ClientManager cm : clients) {
			if (cm.getName() != null) {
				sb.append(cm.getName());
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public void received(String buffer) {
		String[] split = buffer.split(":");
		String name = split[0];
		int corsia_finalJump = Integer.parseInt(split[2]); 
		Direction direction = Direction.valueOf(split[1]);
			gameManager.getWorld().getPlayers(name).setDirection(direction);
			gameManager.getWorld().getPlayers(name).setCorsia(corsia_finalJump);
	}

	public void setReady(final ClientManager clientManager) {
		synchronized (readyClients) {
			readyClients.add(clientManager);
			if (readyClients.size() == 2) {
				dispatch("#START", null);
				System.out.println("ServerGameManager.setReady()");
			}
		}
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}

//	public void startGame() throws IOException {
//		List<String> names = new ArrayList<>();
//		for (final ClientManager cm : clients) {
//			cm.setup();
//			new Thread(cm, cm.toString()).start();
//			names.add(cm.getName());
//		}
//		gameManager = new GameManager();
//		gameManager.start(new Runnable() {
//			@Override
//			public void run() {
//				String statusToString = gameManager.statusToString();
//				dispatch(statusToString, null);
//			}
//		}, names);
//		
		
//	}

	@Override
	public void run() {
			try {
			for(ClientManager cm : clients){
				cm.setup();
				cm.run();
			}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		running = true;
		while(running){
			
		}
	}
}
