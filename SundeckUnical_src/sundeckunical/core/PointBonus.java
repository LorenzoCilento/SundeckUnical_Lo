package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.Animation;
import sundeckunical.gui.GamePanel;
import sundekunical.sfx.SoundEffectProvider;

public class PointBonus extends Bonus{
	
	private static int WIDTH=60;
	private static int HEIGHT=60;
	
	private Animation animation_bonus;
	
	private static int points=20;
	private static TypeOfBonus power = TypeOfBonus.POINT_BONUS;
	
	public PointBonus(int x, int y,Direction direction, int corsia) {
		super(x,y,direction,corsia);
		animation_bonus = new Animation(100, GamePanel.getMyImageProvider().getPointBonus());
		setSound(SoundEffectProvider.getCoinsSound());
		System.out.println("point bonus "+toString());
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
		this.points+=points;
	}
	
	@Override
	public int getPoints()
	{
		return points;
	}
	
	@Override
	public void draw(Graphics g){
//		g.drawImage(animation_bonus.getCurrentFrame(),getX(),getY(),getWIDTH(),getHEIGHT(),null);
		g.drawImage(GamePanel.getMyImageProvider().getObstacle(),getX(),getY(),getWIDTH(),getHEIGHT(),null);
	}

	@Override
	public int getWIDTH() {
		return WIDTH;
	}

	@Override
	public int getHEIGHT() {
		return HEIGHT;
	}

	public Animation getAnimation_bonus() {
		return animation_bonus;
	}

	public void setAnimation_bonus(Animation animation_bonus) {
		this.animation_bonus = animation_bonus;
	}

	@Override
	public void update() {
		synchronized (getLock()) {
			if(isCaptured())
				getSound().play();
			animation_bonus.tick();
		}
	}

	@Override
	public String toString() {
		return "PointBonus [corsia=" + getCorsia() + ", x=" + getX() + ", y="
				+ getY() + ", direction=" + getDirection() + "]";
	}
	
	@Override
	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
		
			sb.append(getCorsia()+":"+getY());
		
		return sb;
	}
}
