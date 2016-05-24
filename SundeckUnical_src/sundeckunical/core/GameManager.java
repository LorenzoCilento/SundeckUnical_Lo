package sundeckunical.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import sundeckunical.gui.NetworkManager;
import sundeckunical.sfx.SoundEffectProvider;

public class GameManager {

	private static World world;
	private WorldManager worldManager;
	private boolean running;

	private final List<Score> scores = new ArrayList<Score>();
	
//camera
	private static int viewCamera = 600;
	private int offSetMinY = 0;
	private int offSetMaxY;
	private int camY;
	private static final int distanceToExplosion = 50;
	
	private NetworkManager manager = null;

	public GameManager() {
		System.out.println("gameManager");
	}
	
	public void loadWorld() {
		worldManager=new WorldManager();
		worldManager.loadWorld("level/defaultLevel.txt");
		world = worldManager.getWorld();
		world.setWIDTH(worldManager.getWorld().getWIDTH());
		world.setHEIGHT(worldManager.getWorld().getHEIGHT());
		world.setDeviation(world.getWIDTH()/world.getNumberLane());
		world.setCorsie();
	}
	
	public void setupObject(List<String> multiPlayerName){
		world.setupPlayer(multiPlayerName);
		
		for(Entry<String, Player> e : getWorld().getMultiPlayerMap().entrySet()){
			e.getValue().setWorld(world);
		}
		
		world.setObjects(worldManager.getWorld().getObjects());
		System.out.println("object "+getWorld().getObjects().size());
		for(AbstractMovableObj o : getWorld().getObjects())
				o.toString();
		
		world.setBonus(worldManager.getWorld().getBonus());
		System.out.println("object "+getWorld().getBonus().size());
		for(Bonus b : getWorld().getBonus())
			b.toString();
		loadScores();
	}
	
	
//creo il mondo
//	public void initializeGameManager(){
//		worldManager=new WorldManager();
//		worldManager.loadWorld("level/defaultLevel.txt");
//		world = worldManager.getWorld();
//		world.setWIDTH(worldManager.getWorld().getWIDTH());
//		world.setHEIGHT(worldManager.getWorld().getHEIGHT());
//		world.setDeviation(world.getWIDTH()/world.getNumberLane());
//		world.setCorsie();
//		setOffSetMaxY(world.getHEIGHT() - viewCamera);
//		world.setPlayer(new Player(world.getCorsie()[Player.defaultCorsia], world.getHEIGHT()));
//		world.getPlayer().setWorld(world);
////		Enemy enemy = new Enemy(world.getCorsie()[2], world.getHEIGHT()+100, Direction.UP, 2);
////		world.setEnemy(enemy);
////		world.getEnemy().setPrecisionDeviation(12);
////		world.getEnemy().setWorld(world);
////		world.center(world.getEnemy());
//		world.setObjects(worldManager.getWorld().getObjects());
//		world.setBonus(worldManager.getWorld().getBonus());
//		loadScores();
//	}
	
	public void updateOffset() {
		for(Player p : getWorld().getMultiPlayerMap().values()){
			camY = p.getY() - (viewCamera-(viewCamera/5));
			if (camY > offSetMaxY)
			    camY = offSetMaxY;
			else if (camY < offSetMinY)
				camY = offSetMinY;
		}
	}
	
	public void setOffSetMinY(int offSetMinY) {
		this.offSetMinY = offSetMinY;
	}

	public void setOffSetMaxY(int offSetMaxY) {
		this.offSetMaxY = offSetMaxY;
	}

	public int getOffSetMinY() {
		return offSetMinY;
	}
	
	public int getOffSetMaxY() {
		return offSetMaxY;
	}
	
	public int getCamY() {
		return camY;
	}
	
	public static World getWorld(){
		return world;
	}
	
	  public void saveScore(final String name)
	    {

	        final Score score = new Score(name, getWorld().getPlayer().getScore());
	      
	        if(scores.size() <5){
	          	scores.add(score);
	          	Collections.sort(scores);
	        }
	        
	        else{
	        	for(Score s : scores){
	        		if(s.getScore() < score.getScore())
	        			s=score;
	        	}
	        }  
			try {
				PrintWriter pw;
				pw = new PrintWriter("resources/file/scores.txt");
				for (final Score s : scores)
		        {
		            pw.append(s.toString());
		            pw.println();
		        }
				scores.clear();
		        pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        

	    }
	   private void loadScores()
	    {
		   try{
			   final BufferedReader br = new BufferedReader(new FileReader("resources/file/scores.txt"));
			   try{			       
			        String buffer = br.readLine();
			        while (buffer != null)
			        {
			            scores.add(new Score(buffer));  
			            buffer = br.readLine();
			        }
			        
			   }
			   finally{
				   br.close();
			   }
		   }catch (IOException e){
			   e.printStackTrace();
		   }
	    }
	   
	public String statusToString() {
		return getWorld().statusToString();
	}
	
	public void parseStatusFromString(String status){
		getWorld().parseStatusFromString(status);
	}
	
	public NetworkManager getNetworkManager() {
		return manager;
	}

	public void setNetworkManager(NetworkManager manager) {
		this.manager = manager;
	}

	public void start(final Runnable runnable, List<String> multiPlayerNames)
	{
			System.out.println("run load gamemanager");
			loadWorld();
			setupObject(multiPlayerNames);
			
			for(Player p : getWorld().getMultiPlayerMap().values()){
				System.out.println("set");
				p.setAlive(true);
				System.out.println("Alive " + p.isAlive());
				p.setStartGameTime(System.currentTimeMillis());
				System.out.println("Time " + p.getStartGameTime());
			}

		running=true;
		
		if(!getWorld().isAlive())
			getWorld().setAlive(true);
		
		new Thread("GameManager") {
			@Override
			public void run() {
				while(running) {
					for(Player p : getWorld().getMultiPlayerMap().values()){
					for( AbstractMovableObj mov : world.getObjects() ){
						if(Collision.checkTypeCollision(p, mov) != TypeOfCollision.ANYTHING && !mov.isCaptured()&&!p.isArrivedJumping()){
							if(Collision.checkTypeCollision(p, mov) == TypeOfCollision.LEFT_RIGHT || 
									Collision.checkTypeCollision(p, mov) == TypeOfCollision.RIGHT_LEFT ){
								if(IdentifiesCollision.shallowCollision(p, mov)){
									if(p.getDirection()==Direction.LEFT) {
										p.setDirection(Direction.RIGHT);
										p.setCorsia(p.getCorsia()+1);
//										world.getEnemy().setDirection(Direction.RIGHT);
//										world.getEnemy().setCorsia(world.getEnemy().getCorsia()+1);
//										world.getEnemy().setY(world.getEnemy().getY()-100);
									}
									else if(p.getDirection()==Direction.RIGHT) {
										p.setDirection(Direction.LEFT);
										p.setCorsia(p.getCorsia()-1);
//										world.getEnemy().setDirection(Direction.LEFT);
//										world.getEnemy().setCorsia(world.getEnemy().getCorsia()-1);
//										world.getEnemy().setY(world.getEnemy().getY()-100);
									}
									break;
								}
							}
						
						
						
							else{
								p.setAlive(false);
								running=false;
							}
						}	
					}
					
					
//					if(Collision.checkTypeCollision(world.getPlayer(), world.getEnemy()) == TypeOfCollision.DOWN_UP) {
//						world.getPlayer().setAlive(false);
//						running=false;
//					}
					
					
						if( p.getY() <= 0 ){
						running=false;
					}
					
					for( Bonus mov : world.getBonus() ){
						if( Collision.checkTypeCollision(p, mov) != TypeOfCollision.ANYTHING){
							 mov.setCurrentTime(System.currentTimeMillis()-p.getStartGameTime());
							 mov.setCaptured(true);
							 mov.update();
							 p.add(mov);
							 p.actionPowerUp(mov);
							 world.remove(mov);
							 break;
						}
						if(p.getY()+300<mov.getY()){
							world.remove(mov);
							break;
						}
					}
					
					
					
						for(AbstractMovableObj a : world.getObjects()) {
							if(p.isExplosion()){
							if(a.getY() < p.getY() && a.getY() > p.getY() - distanceToExplosion && a.getCorsia() == p.getCorsia()) {
									a.setCaptured(true);
								}
							}
						}
					
						
					for(AbstractMovableObj o : world.getObjects()) {
						if(((Obstacle) o).getExplosion_animation().getIndex() == ((Obstacle) o).getExplosion_animation().getSizeFrame() - 1) {
							world.remove(o);
							break;
						}
					}
					
					
				p.update();
				
				for(AbstractMovableObj o : world.getObjects()) {
					o.update();
					if(p.getY()+300<o.getY()){
						world.remove(o);
						break;
					}
				}
			
				
				for(Bonus b : world.getBonus())
					b.update();
				
//				world.getEnemy().update();
				p.checkActiveBonus();
//				updateOffset();
				
				
				if(p.isDied()&& !running){
					p.scoreForDistance();
//					saveScore("Rocco");
					System.out.println("Punti.....:" + p.getScore());
				}
				}
					
				
				runnable.run();
				try {
					Thread.sleep(30);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}//end while
				
		}
			}.start();
	}

	public void startNetworkGame(List<String> allPlayerNames) {
		loadWorld();
		setupObject(allPlayerNames);
		getWorld().setAlive(true);
		
	}
}
