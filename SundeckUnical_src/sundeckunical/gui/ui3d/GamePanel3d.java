package sundeckunical.gui.ui3d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import sundeckunical.core.Direction;
import sundeckunical.core.GameManager;

public class GamePanel3d extends SimpleApplication{

	private final GameManager gameManager;
	private Object monitor = new Object();
	
	private Spatial player;
	private Vector3f position;
	private Node nodoPonte;
	private Spatial ponte; 
	
	private Map<Spatial, Vector3f> nodesPositionMap = new HashMap<>();
	private Set<Spatial> toRemove = new HashSet<>();
	
	public GamePanel3d() {
		gameManager = new GameManager();
//		gameManager.initializeGameManager();
		gameManager.getWorld().getPlayer().setAlive(true);
		gameManager.getWorld().getPlayer().setStartGameTime(System.currentTimeMillis());
	}
	
	@Override
	public void simpleInitApp() {
		
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		final ChaseCamera chaseCamera = setCamera();
		createLights();
		
		player = assetManager.loadModel("Models/Buggy/Buggy.j3o");
		player.rotate(0, (float) Math.PI, 0);
//		Material matPlayer = new Material(assetManager,
//				"Common/MatDefs/Light/Lighting.j3md");
//		player.setMaterial(matPlayer);
		
		createTemplates();
//		createTemplates2();
//		caricaPonte();
//		nodoPonte.attachChild(player);	
		rootNode.attachChild(player);
		
		chaseCamera.setSpatial(player);
		player.addControl(chaseCamera);
		initKeys();
		gameManager.start(new Runnable() {
		
			@Override
			public void run() {
				synchronized (monitor) {
					position = new Vector3f(gameManager.getWorld().getPlayer().getX(),0,gameManager.getWorld().getPlayer().getY());
					nodesPositionMap.clear();
					System.out.println(gameManager.getWorld().getPlayer().getX());
				}	
			}
		}, null);
		
	}
	public void caricaPonte() {
		ponte = assetManager.loadModel("/model3D/bridge/bridge.j3o");
		Material matPonte = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		ponte.setMaterial(matPonte);
		nodoPonte = new Node();
		nodoPonte.attachChild(ponte);
	}
	
	@Override
	public void simpleUpdate(float tpf) {
		synchronized (monitor) {
			player.setLocalTranslation(position);
			for (Entry<Spatial, Vector3f> e : nodesPositionMap.entrySet()) {
				if (e.getKey().getParent() == null) {
					rootNode.attachChild(e.getKey());
				}
				e.getKey().setLocalTranslation(e.getValue());
			}
			for (Spatial s : toRemove) {
				rootNode.detachChild(s);
			}
			toRemove.clear();
		}
//		ponte.setLocalTranslation(new Vector3f(650,-10,position.z));
	}
	
	
	
	
	private void initKeys() {
		ActionListener actionListener = new ActionListener() {

			@Override
			public void onAction(String name, boolean isPressed, float tpf) {
				if ("Left".equals(name) && (gameManager.getWorld().getPlayer().getCorsia() - 1) > 0)  {
					if(GameManager.getWorld().getPlayer().getDirection()!=Direction.LEFT){
						gameManager.getWorld().getPlayer().setCorsia(gameManager.getWorld().getPlayer().getCorsia() - 1);
						gameManager.getWorld().getPlayer().setDirection(Direction.LEFT);
						System.out.println(gameManager.getWorld().getPlayer().getCorsia());
					}
				}
				if ("Right".equals(name) && ((gameManager.getWorld().getPlayer().getCorsia() + 1) <= gameManager.getWorld().getNumberLane() )) {
					if(GameManager.getWorld().getPlayer().getDirection()!=Direction.RIGHT){
						gameManager.getWorld().getPlayer().setCorsia(gameManager.getWorld().getPlayer().getCorsia() + 1);
						gameManager.getWorld().getPlayer().setDirection(Direction.RIGHT);
						System.out.println(gameManager.getWorld().getPlayer().getCorsia());
					}
				}
			}
		};
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addListener(actionListener, "Left", "Right");
	}
	
	private void createLights() {
		AmbientLight ambientLight = new AmbientLight();
		ambientLight.setColor(ColorRGBA.White.mult(0.4f));
		rootNode.addLight(ambientLight);

		final DirectionalLight light1 = new DirectionalLight();
		light1.setColor(ColorRGBA.White.mult(0.3f));
		light1.setDirection(new Vector3f(1, -1, 1).normalizeLocal());
		rootNode.addLight(light1);

		final DirectionalLight light = new DirectionalLight();
		light.setColor(ColorRGBA.White.mult(0.8f));
		light.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
		rootNode.addLight(light);
	}
	
	public void createTemplates() {
		//inizializzo terreno
		Box floorBox = new Box(gameManager.getWorld().getWIDTH(), 0, gameManager.getWorld().getHEIGHT());
		Geometry floor = new Geometry("Floor", floorBox);
		Material matFloor = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		matFloor.setBoolean("UseMaterialColors", false);
		matFloor.setFloat("Shininess", 80f);
		matFloor.setTexture("DiffuseMap",
				assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
		matFloor.setTexture("NormalMap", assetManager
				.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
		floor.setMaterial(matFloor);
		floor.setLocalTranslation(gameManager.getWorld().getWIDTH()/2, 0, 0);
		rootNode.attachChild(floor);
	}
	public void createTemplates2() {
		//inizializzo terreno
		Box floorBox = new Box(gameManager.getWorld().getWIDTH(), 0, gameManager.getWorld().getHEIGHT());
		Geometry floor = (Geometry) assetManager.loadModel("model3D/bridge/bridge.j3o");
		Material matFloor = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		floor.setMaterial(matFloor);
		floor.setLocalTranslation(gameManager.getWorld().getWIDTH()/2, 0, 0);
		rootNode.attachChild(floor);
	}
	
	
	private ChaseCamera setCamera() {
//		setDisplayStatView(false);
//		setDisplayFps(false);
//		flyCam.setMoveSpeed(30f);
		flyCam.setEnabled(false);
		
		final ChaseCamera chaseCamera = new ChaseCamera(cam);
		chaseCamera.setDefaultDistance(80);
		chaseCamera.setDefaultHorizontalRotation(1.57f);
		chaseCamera.setDefaultVerticalRotation(0.2f);
		chaseCamera.setLookAtOffset(new Vector3f(0, 15, 0));
		return chaseCamera;
	}

	public static void main(String[] args) {
		GamePanel3d gamePanel3d = new GamePanel3d();
		gamePanel3d.start();
		
	}
}
