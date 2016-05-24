/**
 * 
 */
package sundeckunical.core;

/**
 * @author Rocco
 *
 */

interface MovableObj {
	
	Direction getDirection();
	
	void setDirection(Direction d);
	
	int getX();
	
	int getY();
	
	void setX(int x);
	
	void setY(int y);
	
	boolean isAlive();
	
	void setAlive(boolean alive);
	
	boolean isDied();
	
	boolean isMoving();
	
	void setMoving(boolean moving);
	
	boolean isArrivedJumping();
	
	void update();
	
}
