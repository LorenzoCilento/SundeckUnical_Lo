package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.GamePanel;
import sundekunical.sfx.SoundEffectProvider;

public class Enemy extends AbstractMovableObj{
	
	public static final int WIDTH=45;
	public static final int HEIGHT=60;
	
	static int speed=3;
	
	public Enemy(final int x,final int y, Direction direction,int corsia) {
		super(x,y,direction,corsia);
		setSpeed(speed);
	}

	@Override
	public int getWIDTH() {
		return WIDTH;
	}

	@Override
	public int getHEIGHT() {
		return HEIGHT;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	public void draw(Graphics g){
		g.drawImage(GamePanel.getMyImageProvider().getPlayerUp()[0],getX(),getY(),getWIDTH(),getHEIGHT(),null);
	}
	
	@Override
	public SoundEffectProvider getSound() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "Enemy [speed=" + getSpeed() + ", x=" + getX() + ", y="
				+ getY() + ", direction=" + getDirection() + "]";
	}
	
	@Override
	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
		
			sb.append(getX()+":"+getY());
		
		return sb;
	}
}
