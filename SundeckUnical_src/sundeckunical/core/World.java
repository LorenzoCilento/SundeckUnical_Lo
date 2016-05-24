package sundeckunical.core;

import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sundeckunical.gui.GamePanel;
import sundeckunical.gui.NetworkManager;
import sundeckunical.gui.ViewCamera;

public class World {
	

	protected int WIDTH;
	protected int HEIGHT;
	
	private int numberLane;			//numero di corsie a disposizione del player
	protected static int [] corsie;
	
	private static final String DEFAULT_PLAYER_NAME = "PLAYER_1";
	Map<String, Player> multiPlayerMap;
	Map<String, ViewCamera> multiViewCamera;
//	private Enemy enemy;
	private int deviation;  //scostamento delle corsie: getWidth/world.numCorsie
	private String idWorld;
	
	private LinkedList<AbstractMovableObj> objects;
	private LinkedList<Bonus> bonus;
	
	private boolean alive = false;

	
	public World(final String idWorld,final int numberLane) {
		this.idWorld=idWorld;
		this.numberLane=numberLane;
		corsie=new int [numberLane+1];
		objects=new LinkedList<AbstractMovableObj>();
		bonus=new LinkedList<Bonus>();
		System.out.println("creo world");
		
	}
	

/*  Funzione che centra nella giusta corsia(identificata dal valore della x corrispondente) 
*   un qualsiase oggetto in base alla sua dimensione  
*/	
//	public void center(final AbstractMovableObj mov){
//		mov.setX(mov.x - (mov.getWIDTH()/2));
//	}
	
	
	public LinkedList<AbstractMovableObj> getObjects() {
		return objects;
	}
	
	public void setObjects(LinkedList<AbstractMovableObj> objects) {
		this.objects = objects;
	}	
	
	public LinkedList<Bonus> getBonus() {
		return bonus;
	}

	public void setBonus(LinkedList<Bonus> bonus) {
		this.bonus = bonus;
	}

	public void addObject(final AbstractMovableObj o) {
		if( o instanceof Obstacle )
			objects.add(o);
		else if( o instanceof Bonus )
			bonus.add((Bonus)o);
	}
	
	public void remove(final AbstractMovableObj obj){
		if( obj instanceof Obstacle )
			objects.remove(obj);
		else if( obj instanceof Bonus )
			bonus.remove(obj);			
	}
	
	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public int getDeviation() {
		return deviation;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public void setWIDTH(final int wIDTH) {
		WIDTH = wIDTH;
	}

	public void setHEIGHT(final int hEIGHT) {
		HEIGHT = hEIGHT;
	}
	
	public void setCorsie(){
		corsie[0]=0;
		for( int i=1; i<corsie.length; i++ ){
			int limiteCorsia= getDeviation() * i;
			corsie[i]=limiteCorsia - (getDeviation()/2) ;
		}
	}
	
	public int [] getCorsie(){
		return corsie;
	}
	
	public Player getPlayer(){
		return multiPlayerMap.get(DEFAULT_PLAYER_NAME);
	}
	
	public Player getPlayers(String name) {
		return multiPlayerMap.get(name);
	}
	
	
	public ViewCamera getViewCamera(){
		return multiViewCamera.get(DEFAULT_PLAYER_NAME);
	}
	
	public ViewCamera getViewCameras(String name) {
		return multiViewCamera.get(name);
	}
	
	public Collection<Player> getPlayerValue(){
		return multiPlayerMap.values();
	}
	
	public int getNumberLane() {
		return numberLane;
	}


	public void setNumberLane(int numberLane) {
		this.numberLane = numberLane;
	}
	
	public void setIdWorld(String idWorld){
		this.idWorld=idWorld;
	}
	
	public String getIdWorld(){
		return idWorld;
	}
	
	public void setupPlayer(List<String> names){
		multiPlayerMap = new HashMap<String,Player>();
		multiViewCamera = new HashMap<String,ViewCamera>();
		if(names == null){
			multiPlayerMap.put(DEFAULT_PLAYER_NAME, new Player(getCorsie()[Player.defaultCorsia], getHEIGHT(),Direction.UP,Player.defaultCorsia));
			multiViewCamera.put(DEFAULT_PLAYER_NAME, new ViewCamera(getHEIGHT()));
		}
		else {
			int i = 1;
			for(String n : names){
				System.out.println("setup player "+n);
				getMultiPlayerMap().put(n, new Player(getCorsie()[i], getHEIGHT(),Direction.UP,i));
				multiViewCamera.put(n, new ViewCamera(getHEIGHT()));
				i++;
			}
		}
		inizializzaPlayer();
	}
	
	public void inizializzaPlayer() {
		for(Player p : getMultiPlayerMap().values()){
			p.setAlive(true);
			p.setStartGameTime(System.currentTimeMillis());
		}
	}

//	public Enemy getEnemy() {
//		return enemy;
//	}
//
//
//	public void setEnemy(Enemy enemy) {
//		this.enemy = enemy;
//	}
	
	public Map<String, Player> getMultiPlayerMap() {
		return multiPlayerMap;
	}
	
	public void parseStatusFromString(String status, NetworkManager networkManager){
		String[] elements = status.split("#");
		String[] player = elements[0].split(";");
		String[] object = elements[1].split(";");
		String[] bonus = elements[2].split(";");
		
//		multiPlayerMap.clear();
		for(String s : player){
			String[] split = s.split(":");
			Direction direction = Direction.valueOf(split[2]);
			multiPlayerMap.get(split[4]).setX(Integer.parseInt(split[0]));
			multiPlayerMap.get(split[4]).setY(Integer.parseInt(split[1]));
			multiPlayerMap.get(split[4]).setCorsia(Integer.parseInt(split[3]));
			multiPlayerMap.get(split[4]).setDirection(direction);
		}
		this.objects.clear();
		if(object.length > 1 || object.length == 1 && !object[0].trim().isEmpty()){
			for(String s : object) {
				String[] split = s.split(":");
				if(split[2].toString().equals("sundeckunical.core.Obstacle"))
					this.objects.add(new Obstacle(getCorsie()[Integer.parseInt(split[0])], Integer.parseInt(split[1]), Direction.STOP, Integer.parseInt(split[0])));
			}
		}
		this.bonus.clear();
		if(bonus.length > 1 || bonus.length == 1 && !bonus[0].trim().isEmpty()){
			for(String s : bonus) {
				String[] split = s.split(":");
				if(split[2].toString().equals("sundeckunical.core.BombBonus"))
					this.objects.add(new BombBonus(getCorsie()[Integer.parseInt(split[0])], Integer.parseInt(split[1]), Direction.STOP, Integer.parseInt(split[0])));
				if(split[2].toString().equals("sundeckunical.core.PointBonus")){
					PointBonus pointBonus = new PointBonus(getCorsie()[Integer.parseInt(split[0])], Integer.parseInt(split[1]), Direction.STOP, Integer.parseInt(split[0]));
					pointBonus.getAnimation_bonus().setIndex(Integer.parseInt(split[3]));
					pointBonus.toString();
					this.objects.add(pointBonus);
				}
					if(split[2].toString().equals("sundeckunical.core.SpeedBonus"))
					this.objects.add(new SpeedBonus(getCorsie()[Integer.parseInt(split[0])], Integer.parseInt(split[1]), Direction.STOP, Integer.parseInt(split[0])));
			}
		}
	}
	
	public String statusToString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, Player> e : multiPlayerMap.entrySet()){
			sb.append(e.getValue().getX()+":"+e.getValue().getY()+":"+e.getValue().getDirection()+":"+e.getValue().getCorsia()+":"+e.getKey()+";");
		}
		sb.append("#");
		if(objects.isEmpty())
			sb.append(" ");
		else{
			for(AbstractMovableObj o : objects){
				sb.append(o.getCorsia()+":"+o.getY()+":"+o.getClass().getName()+";");
			}
		}
		sb.append("#");
		if(bonus.isEmpty())
			sb.append(" ");
		else{
			for(Bonus b : bonus){
				sb.append(b.getCorsia()+":"+b.getY()+":"+b.getClass().getName()+":"+b.getAnimation_bonus().getIndex()+";");
			}
		}
		return sb.toString();
	}


	public void draw(Graphics g){
		int dy = 0;
		int dim = getHEIGHT()/600;
		
		
		for (int i=0;i<dim;i++) {
			g.drawImage(GamePanel.getMyImageProvider().getStreet(), 0, dy, getWIDTH(), 800,  null);
			dy+=800;
		}
		
		for( AbstractMovableObj mov : objects )
			if(mov instanceof Obstacle)
				((Obstacle) mov).draw(g);
			
		for(Bonus b : bonus)
			b.draw(g);
		
	}

}
