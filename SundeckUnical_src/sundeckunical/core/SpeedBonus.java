package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.Animation;
import sundeckunical.gui.GamePanel;
import sundeckunical.sfx.SoundEffectProvider;

public class SpeedBonus extends Bonus{
	
	private static int WIDTH=50;
	private static int HEIGHT=50;
	
	private static int points=20;
	private static TypeOfBonus power=TypeOfBonus.SPEED_BONUS;
	private SoundEffectProvider captured;
	
	public SpeedBonus(int x, int y,Direction direction, int corsia) {
		super(x,y,direction,corsia);
		setDuration(2);
		captured = new SoundEffectProvider("/sfx/capturedBonus.wav", 0);
		
	}
		
	@Override
	void setTypeOfBonus(TypeOfBonus power) {
		this.power=power;
		
	}

	@Override
	TypeOfBonus getTypeOfBonus() {
		return power;
	}

	@Override
	public void setPoints( int points )
	{
		this.points=points;
	}
	
	@Override
	public int getPoints()
	{
		return points;
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(GamePanel.getMyImageProvider().getSpeedBonus(),getX(),getY(),getWIDTH(),getHEIGHT(),null);
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
	public SoundEffectProvider getSound() {
		return captured;
	}
	@Override
	public void update() {
		synchronized (getLock()) {
			if(isCaptured())
				getSound().play();
		}
	}
	@Override
	public String toString() {
		return "Player [speed=" + getSpeed() + ", x=" + getX() + ", y="
				+ getY() + ", direction=" + getDirection() + "]";
	}
	@Override
	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
		
			sb.append(getX()+":"+getY());
		
		return sb;
	}

	@Override
	public Animation getAnimation_bonus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAnimation_bonus(Animation animation_bonus) {
		// TODO Auto-generated method stub
		
	}
	
}
