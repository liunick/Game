package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.EntityCreater;
import entities.Light;
import entities.Player;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {
	
	private static final int GAMEWIDTH = 2;
	private static final int GAMELENGTH = 2;
	
	public MainGameLoop() {

	}

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//hi
		
		/*------------------------------------------------------------------------
		 * TERRAIN GENERATION
		 * 	- rTexture = mud texture
		 * 	- gTexture = grassFlowers texture
		 * 	- bTexture = path texture
		 * 	- blendMap = blendMap texture
		 * 	- backgrounTexture = grass texture
		 * 	- texturePack created
		 --------------------------------------------------------------------------*/

		TerrainTexture rTexture = new TerrainTexture (loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture (loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
	
		
		//EntityCreater entityCreater = new EntityCreater();
		
		/* --------------------------------------------------------------------------
		 *TEXTUREDMODEL CREATION
		 * Loading texture to different models and creating TexturedModel class
		 *  - staticModel = (stall1, stallTexture)
		 *  - grass = (grassModel, grassTexture)
		 *  - fern = (fern, fern)
		 ----------------------------------------------------------------------------*/
		
		TexturedModel staticModel= new TexturedModel(OBJLoader.loadObjModel("stall1", loader), new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		TexturedModel taskBar = new TexturedModel(OBJLoader.load2DObjModel("taskbarModel", loader), new ModelTexture(loader.loadTexture("taskbar")));
		//TexturedModel skyboxModel = new TexturedModel(OBJLoader.loadObjModel("skyboxModel", loader), new ModelTexture(loader.loadTexture("skybox test")));
		
		/*---------------------------------------------------------------------------
		 * TEXTURE PROPERTIES
		 * grass = has transparency
		 * grass = has fake lighting
		 * fern = has transparency
		 ---------------------------------------------------------------------------*/
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		EntityCreater entityCreater = new EntityCreater();
		
		/*---------------------------------------------------------------------------
		 * PREDETERMINED ENTITIES
		 * Create predetermined entities
		 * Will probably change later
		 ---------------------------------------------------------------------------*/
		Entity entity = new Entity(staticModel, new Vector3f(65,0,-50), 0, -120, 0, 2);
		//Entity taskbar = new Entity(taskBar, new Vector2f(1000, 400), 0, 0, 0, 1);
		
		//Entity skybox = new Entity(skyboxModel, new Vector3f())

		List<Entity> entities = new ArrayList<Entity>();
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1,1,1));

		/*---------------------------------------------------------------------------
		 * RANDOMIZED ENTITIES
		 * Creates randomized entities
		 * Will probably change later
		 ---------------------------------------------------------------------------*/
		Random random = new Random();
		for (int i = 0; i < 4000; i++) {
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 2500, 0, random.nextFloat() * -2500), 0, 0, 0, 1));

		}
		for (int i = 0; i < 5000; i++) {
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 2500 - 50, 0, random.nextFloat() * - 2500), 0, 0, 0, 0.6f));
		}
		
		/*---------------------------------------------------------------------------
		 * TERRAIN GENERATION
		 * Creates the terrain.
		 * Will need to work on later
		 ---------------------------------------------------------------------------*/
		Terrain[][] terrain = new Terrain[GAMEWIDTH * 2][GAMELENGTH * 2];
		terrain = createTerrains(loader, texturePack, blendMap, entityCreater);
		
		/*---------------------------------------------------------------------------
		 * CAMERA/PLAYER/RENDERER CREATION
		 * Creates the camera, player and MasterRenderer object
		 * Player has first created TexturedModel for the player and then the player object
		 ---------------------------------------------------------------------------*/
		MasterRenderer renderer = new MasterRenderer();
		TexturedModel playerTexture = new TexturedModel(OBJLoader.loadObjModel("person", loader), new ModelTexture(loader.loadTexture("playerTexture")));
		Player player = new Player(playerTexture, new Vector3f(75, 1, 86), 0, 180, 0, 0.75f);
		Camera camera = new Camera(player);
		
		while (!Display.isCloseRequested()) {	
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				System.exit(0);
			}
			camera.move();
			player.move(terrain[((int)Math.floor(player.getPosition().x/800) + GAMEWIDTH)][((int)Math.floor(-player.getPosition().z/800) + GAMELENGTH)].getReturnYValues());
			//System.out.println(((int)Math.floor(player.getPosition().x/800) + GAMEWIDTH) + ", " + ((int)Math.floor(-player.getPosition().z/800) + GAMELENGTH));
			
			
			//Processes Array of Terrains
			
			for (int x = 0; x < GAMEWIDTH * 2; x++) {
				for (int y = 0; y < GAMELENGTH * 2; y++) {
					renderer.processTerrain(terrain[x][y]);
				}
			}
			
			renderer.processEntity(player);
			renderer.processEntity(entity);
			//renderer.processEntity(taskbar);

			for (Entity testEntity : entities) {
				renderer.processEntity(testEntity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			//System.out.println(DisplayManager.getFrameTimeSeconds());;
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
	public static Terrain[][] createTerrains(Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, EntityCreater entityHost) {
		Terrain[][] createTerrains = new Terrain[GAMEWIDTH * 2][GAMELENGTH * 2];
		for (int x = 0; x < GAMEWIDTH * 2; x++) {
			for (int y = 0; y < GAMELENGTH * 2; y++) {
				createTerrains[x][y] = new Terrain(x - GAMEWIDTH, y - GAMEWIDTH, loader, texturePack, blendMap, "heightmap");
				entityHost.createEntities(x, y);
			}
		}
		return createTerrains;
	}

	public static void createRandomizedEntities(List<Entity> entities) {

	}
}
