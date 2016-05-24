package sundeckunical.core;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import sundeckunical.gui.Animation;
import sundeckunical.gui.GamePanel;
import sundeckunical.gui.NetworkManager;
import sundekunical.sfx.SoundEffectProvider;

public class Player extends AbstractMovableObj implements PowerUp{

	public static final int WIDTH=110;
	public static final int HEIGHT=140;
	
	public static final int defaultCorsia = 2;
	public static final int defaultPrecisionDeviation = 8; //accuratezza del movimento
	public static final int defaultSpeed = 9;
	
	boolean speedBonus = false;
	boolean explosion = false;
	
	private SoundEffectProvider runningSound;
	private SoundEffectProvider walkingSound;
	
	private LinkedList<Bonus> speedBonusAvaible;
	private LinkedList<Bonus> bombBonusAvaible;
	private long startGameTime;
	
	private int lives=5;
	private boolean invisible=false;
	
	private Animation animation_up = new Animation(60, GamePanel.getMyImageProvider().getPlayerUp());
	private Animation animation_left = new Animation(100, GamePanel.getMyImageProvider().getPlayerLeft());
	private Animation animation_right = new Animation(100, GamePanel.getMyImageProvider().getPlayerRight());
	private Animation animation_jump = new Animation(500, GamePanel.getMyImageProvider().getPlayerJump());
	
	public Player(final int x,final int y,Direction direction,int corsia) {
		super(x,y,direction,corsia);
		setSpeed(defaultSpeed);
		setPrecisionDeviation(defaultPrecisionDeviation);
		speedBonusAvaible=new LinkedList<Bonus>();
		bombBonusAvaible=new LinkedList<Bonus>();
		runningSound = new SoundEffectProvider("sfx/running.wav", 0);
		walkingSound = new SoundEffectProvider("sfx/walking.wav", +10);	
		
		System.out.println(toString());
	}
	
	public int getLives(){
		return lives;
	}
	
	public void setLives(int lives){
		this.lives=lives;
	}

	public void scoreForDistance(){
		setScore((int) ((getStartY() - y) * 0.1) ); 
	}
	
	@Override
	public void invisibilityOn() {
		this.invisible=true;
	}
	
	@Override
	public void invisibilityOff() {
		this.invisible=false;
	}
		
	@Override
	public void moreSpeed() {
		setSpeed(getSpeed()+5);
		animation_up.setSpeed(animation_up.getSpeed()-30);
	}
	
	public void explosion() {
		
	}

	public void actionPowerUp(Bonus bonus){
		switch(bonus.getTypeOfBonus()){
			case POINT_BONUS:
				setScore(bonus.getPoints());
				break;
			case SPEED_BONUS:
				moreSpeed();
				setSpeedBonus(true);
				break;
			case BOMB_BONUS:
				setExplosion(true);
				break;
			default:
				break;
		}
	}
	
	@Override
	public int getWIDTH() {
		return WIDTH;
	}

	@Override
	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public long getStartGameTime() {
		return startGameTime;
	}

	public void setStartGameTime(long startGameTime) {
		this.startGameTime = startGameTime;
	}

	public boolean isInvisible(){
		return invisible;
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		int drawY = getY();
		
		if(getDirection()==Direction.UP && !isArrivedJumping() && isSpeedBonus() == true && speedBonusAvaible.size() >=2) {
//			System.out.println("STAMPA 1");
			g2d.drawImage(animation_up.getCurrentFrame(), getX(), drawY, getWIDTH(), getHEIGHT(), null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
			g2d.drawImage(animation_up.getCurrentFrame(), getX(), drawY+10, getWIDTH(), getHEIGHT(), null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
			g2d.drawImage(animation_up.getCurrentFrame(), getX(), drawY+20, getWIDTH(), getHEIGHT(), null);
		}
		if(getDirection()==Direction.UP && !isArrivedJumping() && isSpeedBonus() == true && speedBonusAvaible.size() <2) {
//			System.out.println("STAMPA 2");
			g2d.drawImage(animation_up.getCurrentFrame(), getX(), drawY, getWIDTH(), getHEIGHT(), null);
		}
		if(getDirection()==Direction.UP && isSpeedBonus() == false) {
//			System.out.println("STAMPA 3");
			g2d.drawImage(animation_up.getCurrentFrame(), getX(), getY(), getWIDTH(), getHEIGHT(), null);
		}
		if(getDirection()==Direction.LEFT) {
//			System.out.println("STAMPA 4");
			g2d.drawImage(animation_left.getCurrentFrame(), getX(), drawY, getWIDTH()+20, getHEIGHT()+20, null);
		}
		if(getDirection()==Direction.RIGHT) {
//			System.out.println("STAMPA 5");
			g2d.drawImage(animation_right.getCurrentFrame(), getX(), drawY, getWIDTH()+20, getHEIGHT()+20, null);
		}
		if(isArrivedJumping()){
//			System.out.println("STAMPA 6");
			int jump=25;
				g2d.translate(-jump/2, -jump/2);
				g2d.drawImage(animation_jump.getCurrentFrame(), getX(), getY(), getWIDTH()+jump, getHEIGHT()+jump, null);
		}
		
//		g2d.drawImage(animation_up.getCurrentFrame(), getX(), drawY, getWIDTH(), getHEIGHT(), null);

	}
	
	@Override
	public void update() {
		super.update();
		animationTick();
		playerSound();
		//checkActiveBonus();
	}
//fine interfaccia
	
	public void animationTick(){
		
		if(getDirection()==Direction.UP)
			animation_up.tick();
		else
			animation_up.resetIndex();
		
		if(!isArrivedLeft()) 
			animation_left.tick();
		else
			animation_left.resetIndex();
		
		if(!isArrivedRight())
			animation_right.tick();
		else
			animation_right.resetIndex();
		
	
	}
	
	public void add(Bonus b){
		if(b instanceof SpeedBonus)
			speedBonusAvaible.add(b);
		else if (b instanceof BombBonus)
			bombBonusAvaible.add(b);
	}
	
	public void remove(Bonus b){
		if(b instanceof SpeedBonus)
			speedBonusAvaible.remove(b);
		else if (b instanceof BombBonus)
			bombBonusAvaible.remove(b);
	}
	
	
	public boolean isSpeedBonus() {
		return speedBonus;
	}

	public void setSpeedBonus(boolean speedBonus) {
		this.speedBonus = speedBonus;
	}

	public boolean isExplosion() {
		return explosion;
	}

	public void setExplosion(boolean explosion) {
		this.explosion = explosion;
	}

	public void checkActiveBonus(){
		long currentGameTime=System.currentTimeMillis()-startGameTime;
		for( Bonus b : speedBonusAvaible ){
				if(!b.activeBonus(currentGameTime)){
					switch(b.getTypeOfBonus()){
					case SPEED_BONUS:
						setSpeed(getSpeed()-5);
						animation_up.setSpeed(animation_up.getSpeed()+30);
						break;
					default:
						break;
					}
					speedBonusAvaible.remove(b);
					if(speedBonusAvaible.isEmpty()) {
						setSpeedBonus(false);
					}
					break;
				}
			}
		for(Bonus b : bombBonusAvaible){
			if(!b.activeBonus(currentGameTime)) {
				bombBonusAvaible.remove(b);
				if (bombBonusAvaible.isEmpty()) {
					setExplosion(false);
				}
				break;
			}
		}
		
	}
	@Override
	public SoundEffectProvider getSound() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void playerSound() {
		
		if(getDirection() == Direction.UP) {
			
			walkingSound.loop();
		}
	}
	
	@Override
	public String toString() {
		return "Player [speed=" + getSpeed() + ", x=" + getX() + ", y="
				+ getY() + ", direction=" + getDirection() + "corsia=" + getCorsia() + "]";
	}
	
	@Override
	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
			sb.append(getX()+":"+getY()+":"+getDirection().name());
		
		return sb;
	}
}
