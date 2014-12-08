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

	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		terrainHeight = terrain.getHeightofTerrain(super.getPosition().x, super.getPosition().z);
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
