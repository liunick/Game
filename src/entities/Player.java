package entities;

import models.TexturedModel;
import terrains.Terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 80;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private float terrainHeight = 0;
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private float[] heightVerticies;
	
	public boolean cameraReset = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(float[] terrainVerticies) {
		/*heightVerticies = new float[terrainVerticies.length/3];
		
		for (int x = 1; x < terrainVerticies.length; x += 3) {
			heightVerticies[x] = terrainVerticies[x];
		}
		*/
		checkInputs();
		checkTerrain(terrainVerticies);
		//System.out.println(terrainVerticies[1]);
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		//float dy = (float) (distance)
		super.increasePosition(dx, 0, dz);
		//super.getPosition()
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if (super.getPosition().y > terrainHeight && upwardsSpeed == 0) {
			upwardsSpeed = GRAVITY * DisplayManager.getFrameTimeSeconds();
		}
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			//System.out.println(terrainHeight);
		}
	}
	
	private void jump() {
		this.upwardsSpeed = JUMP_POWER;
	}
	
	private void checkTerrain(float[] allVerticies) {
		//800/255 = 3.1372549019607
		//System.out.println(super.getPosition().x + ", " + super.getPosition().z);
		/*
		 * Let's create a diagram
		 * say below is 1 terrain, with the coordinates of the terrain generation
		 * 
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 800 px
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 0
		 *0										800 px
		 * 
		 * x: 0 -> 800 px		z: 0 -> 800 px
		 *
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa	
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 *aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
		 * x: 0 -> 800			z: -800 -> 0 px
		 *
		 *
		 * V2.66.1 | verts: 16 | faces: 12 | Tris: 24 | Objects: 0.25 | Lamps: 0/1 | Mem:15.19M (0.11M) Cube
		 * Blender cube format 
		 * 
		 * 				
		 */
		for (int y = 0; y < allVerticies.length; y++) {
			int setBegin = 0;
			for (int x = 0; x < allVerticies.length; x++) {
				
			}
		}
		
		
		/*for (int x = 2; x < allVerticies.length; x += 3) {
			System.out.println(allVerticies[x-2] + ", " + allVerticies[x-1] + "," + allVerticies[x]);
		}*/
		
		/*
		for (int z = 2; z < allVerticies.length; z += 3) { //change to x
			//System.out.println(super.getPosition().x + ", " + allVerticies[x]);
			//800/255 = 3.1372549
			// 800/256 = 3.125
			if (allVerticies[z] < super.getPosition().x + 3.12 && allVerticies[z] > super.getPosition().x - 3.12) {
				//System.out.println(allVerticies[x]);
				//System.out.println(super.getPosition().x + ", " + allVerticies[x]);
				//System.out.println("(" + allVerticies[x] + ", " + (super.getPosition().x + 3.12) + ")	(" + allVerticies[x] + ", " + (super.getPosition().x - 3.12) + ")");
				for (int x = z - 2; x < allVerticies.length; x += 255) {
					if (allVerticies[x] < super.getPosition().z + 3.12 && allVerticies[x] > super.getPosition().z - 3.12) {
						//System.out.println()
						terrainHeight = allVerticies[x+1];
						//System.out.println(terrainHeight);
						break;
						//System.out.println(allVerticies[z-2] + ", " + terrainHeight + ", " + allVerticies[z]);
						//System.out.println("(" + super.getPosition().x + ", " + super.getPosition().z + ")" + allVerticies[z-2] + ", " + terrainHeight + ", " + allVerticies[z]);
						//System.out.println());
					}
				}
			}
		}*/
	}
	
	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = RUN_SPEED * 3;
			} else {
				this.currentSpeed = RUN_SPEED;
			}
			
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_C) && !Mouse.isButtonDown(0)) {
			cameraReset = true;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && super.getPosition().y == terrainHeight) {
			jump();
		}
	}

	
}
