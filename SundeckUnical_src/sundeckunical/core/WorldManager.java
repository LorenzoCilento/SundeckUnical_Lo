package sundeckunical.core;

import java.io.BufferedReader;
import java.io.FileReader;

class WorldManager {

	public static String OBSTACLE = "OBSTACLE";
	
	public static String POINT_BONUS = "POINT.BONUS";
	
	public static String SPEED_BONUS = "SPEED.BONUS";
	
	public static String BOMB_BONUS = "BOMB.BONUS";
	
	private World worlds;
	
	public WorldManager(){
	}

	public void setWorld(World world) {
		this.worlds = world;
	}
	
	public World getWorld() {
		return worlds;
	}
	
	public void loadWorld(final String fileName) {

		try {
			String idObject;
			int corsiaObject;
			int yObject;
			Direction direction = Direction.STOP;
			
			int numberLane = 0;
			int widthWorld = 0;
			int heightWorld;
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			String buffer;
			String[] s;
	
			
			buffer = bufferedReader.readLine();
			s = buffer.split("#");
			numberLane = Integer.parseInt(s[0]);
			widthWorld = Integer.parseInt(s[1]);
			heightWorld = Integer.parseInt(s[2]);

			worlds = new World("default",numberLane);
			worlds.setHEIGHT(heightWorld);
			worlds.setWIDTH(widthWorld);
			worlds.setDeviation(widthWorld/numberLane);
			worlds.setCorsie();
			
		
			while((buffer =  bufferedReader.readLine()) != null) {
				s = buffer.split("_");
				idObject = s[0];
				corsiaObject = Integer.parseInt(s[1]);
				yObject = Integer.parseInt(s[2]);

				if(s[3].equals("S")) 
					direction = Direction.STOP;
				
				if(idObject.equals(OBSTACLE))
					worlds.addObject(new Obstacle(worlds.corsie[corsiaObject], yObject, direction,corsiaObject));
				else if( idObject.equals(POINT_BONUS))
					worlds.addObject(new PointBonus(worlds.corsie[corsiaObject], yObject, direction,corsiaObject));
				else if(idObject.equals(SPEED_BONUS))
					worlds.addObject(new SpeedBonus(worlds.corsie[corsiaObject],yObject,direction,corsiaObject));
				else if(idObject.equals(BOMB_BONUS))
					worlds.addObject(new BombBonus(worlds.corsie[corsiaObject], yObject, direction,corsiaObject));
			}
		bufferedReader.close();
		}
			catch (Exception e) {
			}

	}
	
}
