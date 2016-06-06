package sundeckunical.gui;

import sundeckunical.core.*;
import sundeckunical.sfx.SoundEffectProvider;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;

public class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameManager gameManager;
	private static final MyImageProvider myImageProvider= new MyImageProvider();
	private final MainFrame mainframe;
	private SoundEffectProvider bgMusic;
	private SoundEffectProvider swipePlayer;
	int prova=0;
	private static final String DEFAULT_PLAYER_NAME = "PLAYER_1";
	
	private String playerName;
	private int playerCam = 0;
	
	
	public GamePanel(MainFrame mainFrame) {
		this.mainframe=mainFrame;
		setPreferredSize(new Dimension(600, 800));
		this.addKeyListener( new KeyControls() );
		
		bgMusic = new SoundEffectProvider("/music/music2.wav",-30);
		swipePlayer = new SoundEffectProvider("/sfx/swipe.wav",-10);
		
	}

	
/*
 * CLASSE che estende la classe KEYADAPTER per gestire l'input da tastiera
 */
	private class KeyControls extends KeyAdapter{
		@Override
		public void keyPressed(final KeyEvent e){
				
			switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				if((getPlayer().getCorsia() - 1) > 0){
					//blocco la possibilit� di muoversi direttamente di due corsia nella stessa direzione
				//prima di arrivare nella prima corsia di destinazione
					if(getDirection()!=Direction.LEFT){
						getPlayer().setCorsia(getPlayer().getCorsia()-1);
						setDirection(Direction.LEFT);
//						GameManager.getWorld().getEnemy().setCorsia(GameManager.getWorld().getEnemy().getCorsia()-1);
//						GameManager.getWorld().getEnemy().setDirection(Direction.LEFT);
						notifyServer();
						swipePlayer.play();
					}
				}

				break;
			case KeyEvent.VK_RIGHT:
				if((getPlayer().getCorsia() + 1) <= 3){
					//blocco la possibilit� di muoversi direttamente di due corsia nella stessa direzione
					//prima di arrivare nella prima corsia di destinazione
						if(getDirection()!=Direction.RIGHT){
							getPlayer().setCorsia(getPlayer().getCorsia()+1);
							setDirection(Direction.RIGHT);
//							GameManager.getWorld().getEnemy().setCorsia(GameManager.getWorld().getEnemy().getCorsia()+1);
//							GameManager.getWorld().getEnemy().setDirection(Direction.RIGHT);
							notifyServer();
							swipePlayer.play();
						}	
				}
				break;
			case KeyEvent.VK_ESCAPE: //se premo il tasto ESC esco dal gioco
				 System.exit(0);
				break;
			case KeyEvent.VK_SPACE:
				if(!getPlayer().isJumping() && getPlayer().getDirection() == Direction.UP){
					setDirection(Direction.JUMP);
					notifyServer();
				}
				break;	
			default:
				break;
			}
			
		}
		
		@Override
		public void keyReleased(final KeyEvent e){

			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
				swipePlayer.stop();
			}
			
		}
		
		private void setDirection(Direction direction) {
			if (playerName == null) {
				gameManager.getWorld().getPlayer().setDirection(direction);
			} else {
				gameManager.getWorld().getPlayers(playerName).setDirection(direction);
			}
		}	
		
		private Direction getDirection() {
			if (playerName == null) {
				return gameManager.getWorld().getPlayer().getDirection();
			} else {
				return gameManager.getWorld().getPlayers(playerName).getDirection();
			}
		}	
		
		private Player getPlayer() {
			if (playerName == null) {
				return gameManager.getWorld().getPlayer();
			} else {
				return gameManager.getWorld().getPlayers(playerName);
			}
		}

		private void notifyServer() {
			if(gameManager.getClient() != null){
				try {
					gameManager.getClient().notifyMyState();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}	

	private Player getPlayer() {
		if (playerName == null) {
			return gameManager.getWorld().getPlayer();
		} else {
			return gameManager.getWorld().getPlayers(playerName);
		}
	}
	
	private ViewCamera getViewCamera() {
		if (playerName == null) {
			return gameManager.getWorld().getViewCamera();
		} else {
			return gameManager.getWorld().getViewCameras(playerName);
		}
	}

	public static MyImageProvider getMyImageProvider() {
		return myImageProvider;
	}
	
	public GameManager getGameManager(){
		return gameManager;
	}

	public Dimension screenSize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		
		return screenSize;
	}
	
	public void drawScore(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("PUNTI", 21, 101);
		g.drawString("PUNTI", 20, 100);
		g.setColor(Color.ORANGE);
		g.drawString("PUNTI", 19, 99);
		g.drawString("PUNTI", 18, 98);
		g.setColor(Color.BLACK);
		g.drawString(""+getPlayer().getScore(), 81, 151);
		g.drawString(""+getPlayer().getScore(), 80, 150);
		g.setColor(Color.ORANGE);
		g.drawString(""+getPlayer().getScore(), 79, 149);
		g.drawString(""+getPlayer().getScore(), 78, 148);
	}
	
	public void drawMeter(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("METRI", 21, 201);
		g.drawString("METRI", 20, 200);
		g.setColor(Color.ORANGE);
		g.drawString("METRI", 19, 199);
		g.drawString("METRI", 18, 198);
		g.setColor(Color.BLACK);
		g.drawString(""+ (gameManager.getWorld().getHEIGHT() - getPlayer().getY()), 51, 251);
		g.drawString(""+(gameManager.getWorld().getHEIGHT() - getPlayer().getY()), 50, 250);
		g.setColor(Color.ORANGE);
		g.drawString(""+ (gameManager.getWorld().getHEIGHT() - getPlayer().getY()), 49, 249);
		g.drawString(""+ (gameManager.getWorld().getHEIGHT() - getPlayer().getY()), 48, 248);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int nx=tick();
		int nx2=nx-(int)screenSize().getHeight();
		final Map<String,Player> player = gameManager.getWorld().getMultiPlayerMap();
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if(nx==(int)screenSize().getHeight())
			setProva(0);
		g.drawImage(myImageProvider.getAlberi(), 0, nx, (int)screenSize().getWidth(),(int)screenSize().getHeight(),null);
		g.drawImage(myImageProvider.getAlberi(), 0, nx2, (int)screenSize().getWidth(),(int)screenSize().getHeight(),null);
	
		
		
		drawScore(g);
		drawMeter(g);
//		g.translate(+(int)((screenSize().getWidth() - GameManager.getWorld().getWIDTH())/2), - getViewCamera().getCamY());
		g.translate(150, - getViewCamera().getCamY());
		/* disegno i componenti del world */

		GameManager.getWorld().draw(g);	

		Graphics2D g2d = (Graphics2D) g;
		
		if(gameManager.getClient() != null){
			for(Entry<String, Player> p : player.entrySet()){
				if(p.getKey().equals(playerName))
						p.getValue().draw(g);
			}
			
			for(Entry<String, Player> p : player.entrySet()){
				if(!p.getKey().equals(playerName)){
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
					p.getValue().draw(g);
					}
				}
		}
		else{
			for(Entry<String, Player> p : player.entrySet()){
						p.getValue().draw(g);
			}
		}
			
//		GameManager.getWorld().getEnemy().draw(g);
		}
	
	public void startNetwork(GameManager gameManager) {
		this.gameManager = gameManager;
		playerName = gameManager.getWorld().getPlayerName();

		System.out.println("gamepanelNetwork start");
		System.out.println(playerName);
		requestFocus();
		bgMusic.loop();
		gameManager.startNetwork(new Runnable() {
			@Override
			public void run() {
//				playerCam = getViewCamera().getCamY();
				repaint();
			getViewCamera().updateOffset(getPlayer());
//				if(GameManager.getWorld().getPlayer().isDied()){
//					bgMusic.stop();
//					new ScreenGameOver(1000);
//					mainframe.showMenu(MainFrame.MENU_PANEL);
//				}
			}
		});	
	}
	
	public void start() {
		gameManager = new GameManager();
		playerName = null;
		
		requestFocus();
		bgMusic.loop();
		gameManager.start(new Runnable() {
			@Override
			public void run() {
				playerCam = getViewCamera().getCamY();
				repaint();
			getViewCamera().updateOffset(getPlayer());
				if(GameManager.getWorld().getPlayer().isDied()){
					bgMusic.stop();
					new ScreenGameOver(1000);
					mainframe.showMenu(MainFrame.MENU_PANEL);
				}
			}
		});	
	}
	
	public int tick() {
		prova+=2;
		return prova;
	}
	public void setProva(int i) {
		prova=i;
	}
	
	public void immagineSfondo(Graphics g) {
		int dy = tick();
	}
}
