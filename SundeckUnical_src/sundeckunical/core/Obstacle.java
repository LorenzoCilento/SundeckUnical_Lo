package sundeckunical.core;

import java.awt.Graphics;

import sundeckunical.gui.Animation;
import sundeckunical.gui.GamePanel;
import sundeckunical.sfx.SoundEffectProvider;

public class Obstacle extends AbstractMovableObj {

	public static final int WIDTH=100;
	public static final int HEIGHT=50;
	
	
	Animation explosion_animation;
	
	public Obstacle(final int x, final int y,Direction direction, int corsia) {
		super(x,y,direction,corsia);
		explosion_animation = new Animation(80, GamePanel.getMyImageProvider().getExplosion());
		setSound(SoundEffectProvider.getExplosionSound());
	}
		
	public int getWIDTH(){
		return WIDTH;
	}
	
	public int getHEIGHT(){
		return HEIGHT;
	}
	
	public void draw(Graphics g){
		
		if(isCaptured() == false)
			g.drawImage(GamePanel.getMyImageProvider().getObstacle(),getX(),getY(),getWIDTH(),getHEIGHT(),null);
	
		if(isCaptured() == true) {
			g.drawImage(explosion_animation.getCurrentFrame(),getX()-30,getY()-30,getWIDTH()+60,getHEIGHT()+60,null);
			
		}
	}
	
	public Animation getExplosion_animation() {
		return explosion_animation;
	}

	@Override
	public void update() {
		if(isCaptured() && explosion_animation.getIndex()==0)
			getSound().play();
		if(isCaptured()) {
			explosion_animation.tick();
		}
	}
	
	
	@Override
	public String toString() {
		return "Obstacle [speed=" + getSpeed() + ", x=" + getX() + ", y="
				+ getY() + ", direction=" + getDirection() + "]";
	}
	
	@Override
	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
			sb.append(getCorsia()+":"+getY());
		return sb;
	}
}
