package sundeckunical.core;

import sundeckunical.gui.NetworkManager;
import sundekunical.sfx.SoundEffectProvider;

/*
 * QUESTA CLASSE IMPLEMENTA L'INTERFACCIA MOVABLEOBJ,
 * RAPPRESENTA LE AZIONI DI UN GENERICO OGGETTO CHE SI MUOVE
 * PUO' INTRAPRENDERE
 */

public abstract class AbstractMovableObj implements MovableObj {
	
	private int speed=1;
	private int precisionDeviation;
	
	private int corsia;
	private int score=0;
	
	private final Object lock = new Object();
	
	protected int x;
	protected int y;
	private boolean captured = false;
	
	private int startX;
	private int startY;
	private int finalX;
	private int finalY;
	private Direction direction;
	
	
	private boolean isAlive=false;
	private boolean isMoving=false;
	private boolean isJumping=false;
	private boolean isArrivedJumping = false;
	
	private int movement;
	protected World world;
	
	private NetworkManager networkManager = null;
	
	private SoundEffectProvider sound;

	public AbstractMovableObj(int x, int y, Direction direction, int corsia) {
		this.x=this.startX=this.finalX=x-getWIDTH()/2;
		this.y=this.startY=this.finalY=y;
		captured = false;
		this.direction=direction;
		precisionDeviation=1;
		setCorsia(corsia);
	}

/* Da dichiarare nelle sotto classi */
	public abstract int getWIDTH();
	public abstract int getHEIGHT();
	public abstract String toString();
	public abstract StringBuilder toStringBuilder();
	public SoundEffectProvider getSound(){
		synchronized (lock) {
			return sound;
		}
	}
	
	public void setSound(SoundEffectProvider sound) {
		synchronized (lock) {
			this.sound = sound;
		}
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	public int getSpeed(){
		synchronized(lock){
			return speed;
		}
	}
	
	public void setSpeed(int speed){
		synchronized(lock){
			this.speed=speed;
		}
	}
	
	public int getScore() {
		synchronized (lock) {
			return score;
		}		
	}
	
	public int getCorsia() {
		synchronized (lock) {
			return corsia;	
		}		
	}

	public void setCorsia(int corsia) {
		synchronized (lock) {
			this.corsia = corsia;
		}		
	}

	public void setScore(int score) {
		synchronized (lock) {
			this.score += score;
		}
	}

	public Object getLock() {
		return lock;
	}

	public void setPrecisionDeviation(final int precisionDeviation){
		synchronized (lock) {
			this.precisionDeviation=precisionDeviation;	
		}
	}
	
	public int getPrecisionDeviation(){
		synchronized (lock) {
			return precisionDeviation;
		}
	}
	
	public int getMovement() {
		synchronized (lock) {
			return movement;		
		}
	}

	public void setMovement(int spostamento) {
		synchronized (lock) {
			this.movement = spostamento;
		}
	}

	public void setWorld(final World world){
		synchronized(lock){
			this.world=world;
			setMovement(world.getDeviation()/getPrecisionDeviation());
		}
	}

/*
 * Override dell'interfaccia MovableObj	
*/
	
	@Override
	public Direction getDirection() {
		synchronized (lock) {
			return this.direction;
		}
	}

	@Override
	public void setDirection(Direction d) {
		synchronized (lock) {
			this.direction=d;	
		}	
	}
	
	@Override
	public boolean isMoving(){
		synchronized (lock) {
			return isMoving;
		}
	}
	
	@Override
	public void setMoving(boolean isMoving){
		synchronized (lock) {
			this.isMoving=isMoving;
		}
	}

	@Override
	public int getX() {
		synchronized (lock) {
			return this.x;
		}
		
	}

	@Override
	public int getY() {
		synchronized (lock) {
			return y;
		}
	}
	
	@Override
	public void setX(int x) {
		synchronized (lock) {
			this.x = x;	
		}
	}
	
	@Override
	public void setY(int y) {
		synchronized (lock) {
			this.y = y;	
		}
	}
	
	@Override
	public void setAlive(boolean alive){
		synchronized (lock) {
			isAlive=alive;	
		}
	}
	
	@Override
	public boolean isAlive() {
		synchronized (lock) {
			return isAlive;
		}
	}
	
	@Override
	public boolean isDied(){
		synchronized (lock) {
			return !isAlive;	
		}
	}
	
	@Override
	public boolean isArrivedJumping(){
		synchronized(lock){
			if(getY() >= getFinalY()){
				return true;
			}
			return false;
		}
	}
	
	@Override
	public void update() {
		synchronized (lock) {
			switch(direction){
				case UP:
					if( 0 <= (y-getSpeed()) )
						y-=getSpeed();
					else
						y=-1;
					break;
				case JUMP: 
					
					
					if(isArrivedJumping()) {
						
						y -= getSpeed()*3;
						
					}
					else {
						
						setDirection(Direction.UP);
					}
					break;
				case LEFT:
					int diff = Math.abs(getX() - (world.getCorsie()[getCorsia()] - getWIDTH()/2));
					if ( !isArrivedLeft() ){
						if(diff >= getMovement()){
						  x-=getMovement();
						  y-=getSpeed();
						}
						else {
							x-=diff;
							y-=getSpeed();
						}
					}
					else
						setDirection(Direction.UP);
					break;
				case RIGHT:	
					int diffe = Math.abs(getX() - (world.getCorsie()[getCorsia()] - getWIDTH()/2));
					if(!isArrivedRight()){
						if(diffe >= getMovement()){
							  x+=getMovement();
							  y-=getSpeed();
						}
						else {
							x+=diffe;
							y-=getSpeed();
						}
					}
					else
						setDirection(Direction.UP);
					break;
				
				default:
//					setDirection(Direction.UP);
					break;
				}
		}
		
	}
	
/* 
 * FINE override interfaccia MovableObj 
*/	
	
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getFinalX() {
		synchronized (lock) {
			return finalX;	
		}
	}

	public void setFinalX(int finalX) {
		synchronized (lock) {
			this.finalX = finalX;	
		}
	}

	public int getFinalY() {
		synchronized (lock) {
			return finalY;	
		}
	}

	public void setFinalY(int finalY) {
		synchronized (lock) {
			this.finalY = finalY;
		}
	}


	public boolean isArrivedRight() {
		synchronized (lock) {
			if(getX()==(world.getCorsie()[getCorsia()] - getWIDTH()/2)) {
				setMoving(false);
				return true;
			}
			setMoving(true);
			setFinalY(getY()+10);
			return false;
		}
	}
	
	public boolean isArrivedLeft() {
		synchronized (lock) {
			if(getX()==(world.getCorsie()[getCorsia()] - getWIDTH()/2 )){	
				setMoving(false);
				return true;
			}
			setMoving(true);
			setFinalY(getY()+10);
			return false;
		}
	}

	public boolean isCaptured() {
		synchronized (lock) {
			return captured;
		}
	}

	public void setCaptured(boolean isCaptured) {
		synchronized (lock) {
			this.captured = isCaptured;
		}
	}

	
	public void setJumping(boolean isJumping) {
		synchronized (lock) {
			this.isJumping = isJumping;
		}
	}

	
	public boolean isJumping() {
		synchronized (lock) {
			return isJumping;
		}
	}

	public void setArrivedJumping(boolean isArrivedJumping) {
		synchronized (lock) {
			this.isArrivedJumping = isArrivedJumping;
		}
	}
}
