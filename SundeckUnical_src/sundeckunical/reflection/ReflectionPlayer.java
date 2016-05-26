package sundeckunical.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sundeckunical.core.Direction;
import sundeckunical.core.Player;

class ReflectionPlayer {
	//fields of Constructor Player
	private static Player player;
	private static int xPlayer;
	private static int yPlayer;
	private static Direction directionPlayer;
	private static int corsiaPlayer;
	
	static Class<?> classPlayer;
	static Constructor<?> [] constructorsPlayer;
	
	static String nameMethodSetLives = "setLives";
	static Method setLivesPlayer;
	
	static String nameMethodSetInvisible = "setInvisible";
	static Method setInvisible;
	
	public ReflectionPlayer() throws NoSuchMethodException, SecurityException{
		classPlayer = Player.class;
		constructorsPlayer = classPlayer.getConstructors();
		setLivesPlayer = classPlayer.getDeclaredMethod(nameMethodSetLives, int.class);
		setInvisible= classPlayer.getDeclaredMethod(nameMethodSetInvisible, boolean.class);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		new ReflectionPlayer();
		 System.out.println("SetLives:"+setLivesPlayer.toString());
	//TYPE 1: createPlayerFromConstructor
		createNewPlayerFromConstructor(2, 3, Direction.LEFT, 5).toString();
		System.out.println(""+getPlayer().toString());
	//TYPE 2: createPlayerFromFields
		initFieldsPlayer(2, 3, Direction.UP, 7);
		System.out.println("Live:"+player.getLives());
		setInvisible(false);
		System.out.println("Invisible:"+player.getInvisible());
 	}
 
	public static Player createNewPlayerFromConstructor(int x, int y, Direction dir,int corsia){
		 int size=constructorsPlayer.length;
		 int i=0;
		 
		 while(i<size){
			 System.out.println("createNewPlayerFromConstructor");
			 if(constructorsPlayer[i].getParameterCount()==4){
			 try {
				 return (player=(Player)(constructorsPlayer[i].newInstance(x,y,dir,corsia)));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {e.printStackTrace();}
			 System.out.println("i:"+constructorsPlayer[i]);}
			 i++;
		 } 
		 return null;
	}
	
	public static void setInvisible(boolean x){
		try {
			setInvisible.invoke(player, x);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void initFieldsPlayer(int x,int y,Direction dir,int corsia){
		 setxPlayer(x);
		 setyPlayer(y);
		 setDirectionPlayer(dir);
		 setCorsiaPlayer(corsia);
		 setPlayer(new Player(x, y, dir, corsia));
		 System.out.println("createNewPlayerFromFields");
		 System.out.println(""+player.toString());
	}
	
	
	
	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		ReflectionPlayer.player = player;
	}

	public static int getxPlayer() {
		return xPlayer;
	}

	public static void setxPlayer(int xPlayer) {
		ReflectionPlayer.xPlayer = xPlayer;
	}

	public static int getyPlayer() {
		return yPlayer;
	}

	public static void setyPlayer(int yPlayer) {
		ReflectionPlayer.yPlayer = yPlayer;
	}

	public static Direction getDirectionPlayer() {
		return directionPlayer;
	}

	public static void setDirectionPlayer(Direction directionPlayer) {
		ReflectionPlayer.directionPlayer = directionPlayer;
	}

	public static int getCorsiaPlayer() {
		return corsiaPlayer;
	}

	public static void setCorsiaPlayer(int corsiaPlayer) {
		ReflectionPlayer.corsiaPlayer = corsiaPlayer;
	}
	 
}//end reflectionPlayer