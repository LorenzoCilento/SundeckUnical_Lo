package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.Animation;


abstract class Bonus extends AbstractMovableObj {
	
	private long currentTime;
	private int duration=1;
	
	public Bonus(int x, int y,Direction direction,int corsia) {
		super(x, y,direction,corsia);
	}
	
	abstract void setTypeOfBonus(TypeOfBonus power);
	
	abstract TypeOfBonus getTypeOfBonus();
	
	abstract public void setPoints( int points );
	
	abstract public int getPoints();
	
	abstract public void draw(Graphics g);

	public void setDuration(int duration){
		this.duration=duration;
	}

	public int getDuration(){
		return duration;
	}
	
	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	
	public abstract Animation getAnimation_bonus();

	public abstract void setAnimation_bonus(Animation animation_bonus);

	
	public boolean activeBonus(long timePlayer){
		long duration= getDuration()*1000;
		if(timePlayer - currentTime > duration ){
			return false;
		}
		return true;
	}
	
}
