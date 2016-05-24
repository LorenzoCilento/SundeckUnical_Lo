package sundeckunical.gui;

import java.awt.Image;

public class Animation {
	
	private int index,speed;
	private long lastTime, timer;
	private Image[] frames;
	
	public Animation(int speed, Image[] frames ) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick() {
		timer+=System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if (timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	
	public void resetIndex() {
		index = 0;
	}
	
	public int getIndex() {
		return index;
	}

	public Image getCurrentFrame () {
		return frames[index];
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSizeFrame() {
		return frames.length;
	}
	
	public void setIndex(int i){
		this.index = i;
	}
	
}
