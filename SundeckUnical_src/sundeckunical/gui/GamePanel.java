package sundeckunical.gui;

import sundeckunical.core.*;
import sundeckunical.sfx.SoundEffectProvider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.Collection;

import javax.swing.*;

public class GamePanel extends JPanel {
	
	private GameManager gameManager;
	private static final MyImageProvider myImageProvider= new MyImageProvider();
	private final MainFrame mainframe;
	private SoundEffectProvider bgMusic;
	private SoundEffectProvider swipePlayer;
	int prova=0;
	
	private String playerName;
	private NetworkManager networkManager;
	private int playerCam = 0;
	
	
	public GamePanel(MainFrame mainFrame) {
		gameManager = new GameManager();
		setPreferredSize(screenSize());
		this.mainframe=mainFrame;
		
		bgMusic = new SoundEffectProvider("/music/music2.wav",-20);
		swipePlayer = new SoundEffectProvider("/sfx/swipe.wav",0);
		this.addKeyListener( new KeyControls() );
		
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
						signalServer(getPlayer().getCorsia());
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
							signalServer(getPlayer().getCorsia());
							swipePlayer.play();
						}	
				}
				break;
			case KeyEvent.VK_ESCAPE: //se premo il tasto ESC esco dal gioco
				 System.exit(0);
				break;
			case KeyEvent.VK_SPACE:
				if(!getPlayer().isJumping() && getPlayer().getDirection() == Direction.UP){
					getPlayer().setJumping(true);
					getPlayer().setFinalY(getPlayer().getY()-(30*getPlayer().getSpeed()));
					setDirection(Direction.JUMP);
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
			if( e.getKeyCode() == KeyEvent.VK_SPACE ){
				getPlayer().setJumping(false);
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

		private void signalServer(int corsia) {
			
			if (networkManager != null) {
				networkManager.dispatch(playerName+":"+ getPlayer().getDirection().name()+":"+corsia);
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
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if(nx==(int)screenSize().getHeight())
			setProva(0);
		g.drawImage(myImageProvider.getAlberi(), 0, nx, (int)screenSize().getWidth(),(int)screenSize().getHeight(),null);
		g.drawImage(myImageProvider.getAlberi(), 0, nx2, (int)screenSize().getWidth(),(int)screenSize().getHeight(),null);
	
		
//		drawScore(g);
//		drawMeter(g);
		
		g.translate(+(int)((screenSize().getWidth() - GameManager.getWorld().getWIDTH())/2), - getViewCamera().getCamY());
		getViewCamera().updateOffset(getPlayer());
		/* disegno i componenti del world */

		final Collection<Player> player = gameManager.getWorld().getPlayerValue();
		GameManager.getWorld().draw(g);	
		for(Player p : player)
			p.draw(g);
//		GameManager.getWorld().getEnemy().draw(g);
	}
	
	public void start() {
//		playerName = null;
//		networkManager = null;
		
		requestFocus();
//		bgMusic.loop();
		gameManager.start(new Runnable() {
			@Override
			public void run() {
				playerCam = getViewCamera().getCamY();
				repaint();
//			getViewCamera().updateOffset(getPlayer());
//				if(GameManager.getWorld().getPlayer().isDied()){
//					bgMusic.stop();
//					new ScreenGameOver(1000);
//					mainframe.showMenu(MainFrame.MENU_PANEL);
//				}
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
	
	GameManager startNetwork(NetworkManager networkManager) {
		this.networkManager = networkManager;
		playerName = networkManager.getPlayerName();
		System.out.println("GamePanel.startNetwork() " + playerName);
//		gameManager.startNetworkGame(networkManager.getAllPlayerNames());
		playerCam = getViewCamera().getCamY();
		this.start();
		requestFocus();
		return gameManager;
	}
}
