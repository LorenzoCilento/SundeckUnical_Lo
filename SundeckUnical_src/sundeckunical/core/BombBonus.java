package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.Animation;
import sundeckunical.gui.GamePanel;
import sundekunical.sfx.SoundEffectProvider;

class BombBonus extends Bonus {

	private static final int WIDTH = 60;
	private static final int HEIGHT = 114;
	
	private static TypeOfBonus power = TypeOfBonus.BOMB_BONUS;
	private Animation animation_bomb;
	
	public BombBonus(int x, int y, Direction direction,int corsia) {
		super(x, y, direction,corsia);
		setDuration(5);
		animation_bomb = new Animation(100, GamePanel.getMyImageProvider().getBombBonus());
		setSound(SoundEffectProvider.getCaptured());
	}
	
	@Override
	public int getWIDTH() {
		return WIDTH;
	}
	
	@Override
	public int getHEIGHT() {
		// TODO Auto-generated method stub
		return HEIGHT;
	}

	public void draw(Graphics g) {
	
		g.drawImage(animation_bomb.getCurrentFrame(), getX(), getY(), getWIDTH(), getHEIGHT(), null);
		
	}

	@Override
	void setTypeOfBonus(TypeOfBonus power) {
		this.power = power;
	}

	@Override
	TypeOfBonus getTypeOfBonus() {
		return power;
	}

	@Override
	public void setPoints(int points) {
		
	}

	@Override
	public int getPoints() {
		return 0;
	}
	
	@Override
	public void update() {
		synchronized (getLock()) {
			if(isCaptured())
				getSound().play();
			animation_bomb.tick();
		}
	}

	@Override
	public String toString() {
		return "BombBonus [speed=" + getSpeed() + ", x=" + getX() + ", y="
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
