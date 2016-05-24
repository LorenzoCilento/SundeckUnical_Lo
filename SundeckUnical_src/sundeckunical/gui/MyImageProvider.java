package sundeckunical.gui;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JLabel;


public class MyImageProvider {

  //Player
	private final Image playerUp[];
	private final Image playerLeft[];
	private final Image playerRight[];
	private final Image playerJump[];
	private final Image pointBonus[];
	private final Image explosion[];
	private final Image bombBonus[];
	
	private final Image playerUp_1;
	private final Image playerUp_2;
	private final Image playerUp_3;
	private final Image playerUp_4;
	private final Image playerUp_5;
	private final Image playerUp_6;
	private final Image playerUp_7;
	private final Image playerUp_8;
	
	private final Image playerLeft_1;
	private final Image playerLeft_2;
	private final Image playerLeft_3;
	private final Image playerLeft_4;
	private final Image playerLeft_5;
	private final Image playerLeft_6;
	
	private final Image playerRight_1;
	private final Image playerRight_2;
	private final Image playerRight_3;
	private final Image playerRight_4;
	private final Image playerRight_5;
	private final Image playerRight_6;
	
	private final Image pointBonus_1;
	private final Image pointBonus_2;
	private final Image pointBonus_3;
	private final Image pointBonus_4;
	private final Image pointBonus_5;
	private final Image pointBonus_6;
	private final Image pointBonus_7;
	
	private final Image explosion_1;
	private final Image explosion_2;
	private final Image explosion_3;
	private final Image explosion_4;
	private final Image explosion_5;
	private final Image explosion_6;
	private final Image explosion_7;
	private final Image explosion_8;
	private final Image explosion_9;
	private final Image explosion_10;
	private final Image explosion_11;
	private final Image explosion_12;
	private final Image explosion_13;
	private final Image explosion_14;
	
	private final Image bombBonus_1;
	private final Image bombBonus_2;
	private final Image bombBonus_3;
	private final Image bombBonus_4;
	private final Image bombBonus_5;
	private final Image bombBonus_6;


  //Strada
	private final Image street;
	
	private final Image auto;
	
	private final Image obstacle;
	
	private final Image speedBonus;
	
	private final Image alberi;

	public MyImageProvider(){
		
		final Toolkit kit= Toolkit.getDefaultToolkit();
		playerUp = new Image[8];
		playerLeft = new Image[6];
		playerRight = new Image[6];
		playerJump = new Image[1];
		pointBonus = new Image[7];
		explosion = new Image[14];
		bombBonus = new Image[10];

		playerUp_1 = kit.getImage("resources/img/new_player/up/PlayerUp_1.png");
		playerUp_2 = kit.getImage("resources/img/new_player/up/PlayerUp_2.png");
		playerUp_3 = kit.getImage("resources/img/new_player/up/PlayerUp_3.png");
		playerUp_4 = kit.getImage("resources/img/new_player/up/PlayerUp_4.png");
		playerUp_5 = kit.getImage("resources/img/new_player/up/PlayerUp_5.png");
		playerUp_6 = kit.getImage("resources/img/new_player/up/PlayerUp_6.png");
		playerUp_7 = kit.getImage("resources/img/new_player/up/PlayerUp_7.png");
		playerUp_8 = kit.getImage("resources/img/new_player/up/PlayerUp_8.png");
		
		playerLeft_1 = kit.getImage("resources/img/new_player/left/PlayerLeft_1.png");
		playerLeft_2 = kit.getImage("resources/img/new_player/left/PlayerLeft_2.png");
		playerLeft_3 = kit.getImage("resources/img/new_player/left/PlayerLeft_3.png");
		playerLeft_4 = kit.getImage("resources/img/new_player/left/PlayerLeft_4.png");
		playerLeft_5 = kit.getImage("resources/img/new_player/left/PlayerLeft_5.png");
		playerLeft_6 = kit.getImage("resources/img/new_player/left/PlayerLeft_6.png");
		
		playerRight_1 = kit.getImage("resources/img/new_player/right/PlayerRight_1.png");
		playerRight_2 = kit.getImage("resources/img/new_player/right/PlayerRight_2.png");
		playerRight_3 = kit.getImage("resources/img/new_player/right/PlayerRight_3.png");
		playerRight_4 = kit.getImage("resources/img/new_player/right/PlayerRight_4.png");
		playerRight_5 = kit.getImage("resources/img/new_player/right/PlayerRight_5.png");
		playerRight_6 = kit.getImage("resources/img/new_player/right/PlayerRight_6.png");
		
		pointBonus_1 = kit.getImage("resources/img/bonus/coins/coins_1.png");
		pointBonus_2 = kit.getImage("resources/img/bonus/coins/coins_2.png");
		pointBonus_3 = kit.getImage("resources/img/bonus/coins/coins_3.png");
		pointBonus_4 = kit.getImage("resources/img/bonus/coins/coins_4.png");
		pointBonus_5 = kit.getImage("resources/img/bonus/coins/coins_5.png");
		pointBonus_6 = kit.getImage("resources/img/bonus/coins/coins_6.png");
		pointBonus_7 = kit.getImage("resources/img/bonus/coins/coins_7.png");
		
		explosion_1 = kit.getImage("resources/img/bonus/explosion/explosion_1.png");
		explosion_2 = kit.getImage("resources/img/bonus/explosion/explosion_2.png");
		explosion_3 = kit.getImage("resources/img/bonus/explosion/explosion_3.png");
		explosion_4 = kit.getImage("resources/img/bonus/explosion/explosion_4.png");
		explosion_5 = kit.getImage("resources/img/bonus/explosion/explosion_5.png");
		explosion_6 = kit.getImage("resources/img/bonus/explosion/explosion_6.png");
		explosion_7 = kit.getImage("resources/img/bonus/explosion/explosion_7.png");
		explosion_8 = kit.getImage("resources/img/bonus/explosion/explosion_8.png");
		explosion_9 = kit.getImage("resources/img/bonus/explosion/explosion_9.png");
		explosion_10 = kit.getImage("resources/img/bonus/explosion/explosion_10.png");
		explosion_11 = kit.getImage("resources/img/bonus/explosion/explosion_11.png");
		explosion_12 = kit.getImage("resources/img/bonus/explosion/explosion_12.png");
		explosion_13 = kit.getImage("resources/img/bonus/explosion/explosion_13.png");
		explosion_14 = kit.getImage("resources/img/bonus/explosion/explosion_14.png");
		
		bombBonus_1 = kit.getImage("resources/img/bonus/bomb/bomb_1.png");
		bombBonus_2 = kit.getImage("resources/img/bonus/bomb/bomb_2.png");
		bombBonus_3 = kit.getImage("resources/img/bonus/bomb/bomb_3.png");
		bombBonus_4 = kit.getImage("resources/img/bonus/bomb/bomb_4.png");
		bombBonus_5 = kit.getImage("resources/img/bonus/bomb/bomb_5.png");
		bombBonus_6 = kit.getImage("resources/img/bonus/bomb/bomb_6.png");
		
		auto = kit.getImage("resources/img/auto.png");
		speedBonus = kit.getImage("resources/img/speed.png");
		street = kit.getImage("Resources/img/bridge.png");
		
		obstacle = kit.getImage("resources/img/obstacle_prosp.png");
		
		alberi = kit.getImage("resources/img/bosco.jpg");
		
		final MediaTracker tracker = new MediaTracker(new JLabel());
		tracker.addImage(street, 1);
		tracker.addImage(playerUp_1, 2);
		tracker.addImage(playerUp_2, 3);
		tracker.addImage(playerUp_3, 4);
		tracker.addImage(playerUp_4, 5);
		tracker.addImage(playerUp_5, 6);
		tracker.addImage(playerUp_6, 50);
		tracker.addImage(playerUp_7, 51);
		tracker.addImage(playerUp_8, 52);
		tracker.addImage(playerLeft_1, 7);
		tracker.addImage(playerLeft_2, 8);
		tracker.addImage(playerLeft_3, 9);
		tracker.addImage(playerLeft_4, 10);
		tracker.addImage(playerLeft_5, 11);
		tracker.addImage(playerLeft_6, 12);
		tracker.addImage(playerRight_1, 13);
		tracker.addImage(playerRight_2, 14);
		tracker.addImage(playerRight_3, 15);
		tracker.addImage(playerRight_4, 16);
		tracker.addImage(playerRight_5, 17);
		tracker.addImage(playerRight_6, 18);
		tracker.addImage(pointBonus_1, 19);
		tracker.addImage(pointBonus_2, 20);
		tracker.addImage(pointBonus_3, 21);
		tracker.addImage(pointBonus_4, 22);
		tracker.addImage(pointBonus_5, 23);
		tracker.addImage(pointBonus_6, 24);
		tracker.addImage(pointBonus_7, 25);
	    tracker.addImage(explosion_1, 26);
	    tracker.addImage(explosion_2, 27);
	    tracker.addImage(explosion_3, 28);
	    tracker.addImage(explosion_4, 29);
	    tracker.addImage(explosion_5, 30);
	    tracker.addImage(explosion_6, 31);
	    tracker.addImage(explosion_7, 32);
	    tracker.addImage(explosion_8, 33);
	    tracker.addImage(explosion_9, 34);
	    tracker.addImage(explosion_10, 35);
	    tracker.addImage(explosion_11, 36);
	    tracker.addImage(explosion_12, 37);
	    tracker.addImage(explosion_13, 38);
	    tracker.addImage(explosion_14, 39);
	    tracker.addImage(bombBonus_1, 40);
	    tracker.addImage(bombBonus_2, 41);
	    tracker.addImage(bombBonus_3, 42);
	    tracker.addImage(bombBonus_4, 43);
	    tracker.addImage(bombBonus_5, 44);
	    tracker.addImage(bombBonus_6, 45);
	    tracker.addImage(auto, 46);
		tracker.addImage(speedBonus, 47);
	    tracker.addImage(obstacle, 48);
	    tracker.addImage(alberi, 49);
	   
	    
	    playerUp[0] = playerUp_1;
	    playerUp[1] = playerUp_2;
	    playerUp[2] = playerUp_3;
	    playerUp[3] = playerUp_4;
	    playerUp[4] = playerUp_5;
	    playerUp[5] = playerUp_6;
	    playerUp[6] = playerUp_7;
	    playerUp[7] = playerUp_8;
	    
	    playerLeft[0] = playerLeft_1;
	    playerLeft[1] = playerLeft_2;
	    playerLeft[2] = playerLeft_3;
	    playerLeft[3] = playerLeft_4;
	    playerLeft[4] = playerLeft_5;
	    playerLeft[5] = playerLeft_6;
	    
	    playerRight[0] = playerRight_1;
	    playerRight[1] = playerRight_2;
	    playerRight[2] = playerRight_3;
	    playerRight[3] = playerRight_4;
	    playerRight[4] = playerRight_5;
	    playerRight[5] = playerRight_6;
	    
	    playerJump[0] = playerUp_2;
//	    playerJump[1] = playerUp_4;
	    
	    pointBonus[0] = pointBonus_1;
	    pointBonus[1] = pointBonus_2;
	    pointBonus[2] = pointBonus_3;
	    pointBonus[3] = pointBonus_4;
	    pointBonus[4] = pointBonus_5;
	    pointBonus[5] = pointBonus_6;
	    pointBonus[6] = pointBonus_7;
	    
	    explosion[0] = explosion_1;
	    explosion[1] = explosion_2;
	    explosion[2] = explosion_3;
	    explosion[3] = explosion_4;
	    explosion[4] = explosion_5;
	    explosion[5] = explosion_6;
	    explosion[6] = explosion_7;
	    explosion[7] = explosion_8;
	    explosion[8] = explosion_9;
	    explosion[9] = explosion_10;
	    explosion[10] = explosion_11;
	    explosion[11] = explosion_12;
	    explosion[12] = explosion_13;
	    explosion[13] = explosion_14;
	    
	    bombBonus[0] = bombBonus_1;
	    bombBonus[1] = bombBonus_2;
	    bombBonus[2] = bombBonus_3;
	    bombBonus[3] = bombBonus_4;
	    bombBonus[4] = bombBonus_5;
	    bombBonus[5] = bombBonus_6;
	    bombBonus[6] = bombBonus_5;
	    bombBonus[7] = bombBonus_4;
	    bombBonus[8] = bombBonus_3;
	    bombBonus[9] = bombBonus_2;
	    
	    
	    try
        {
            tracker.waitForAll();
        }
        catch (final InterruptedException e)
        {
            throw new RuntimeException("images not loaded", e);
        }
	}

	public Image getObstacle(){
		return obstacle;
	}
	
	public Image getStreet() {
		return street;
	}
	
	public Image[] getPointBonus() {
		return pointBonus;
	}

	public Image getAuto() {
		return auto;
	}
	
	public Image[] getPlayerUp() {
		return playerUp;
	}
	
	public Image[] getPlayerLeft() {
		return playerLeft;
	}
	
	public Image[] getPlayerRight() {
		return playerRight;
	}

	public Image[] getPlayerJump() {
		return playerJump;
	}

	public Image getSpeedBonus(){
		return speedBonus;
	}

	public Image[] getBombBonus() {
		return bombBonus;
	}

	public Image[] getExplosion() {
		return explosion;
	}

	public Image getAlberi() {
		return alberi;
	}


}
