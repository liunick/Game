package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private static final float PLAYER_HEIGHT = 8;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	//Camera pitch is always the same so no need of new variable

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;		//Camera's high or low
	private float yaw = 0;			//Left or RIght
	private float roll; 		//How much tilted 180 is upside down
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
	}

	public void move() { 
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		checkCameraReset();
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 -(player.getRotY() + angleAroundPlayer);
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + PLAYER_HEIGHT;
		
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));

	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateZoom() {
		
		float getWheel = Mouse.getDWheel();
		if (getWheel > 1000)
			getWheel = 1000;
		
		if (distanceFromPlayer >= 15 && distanceFromPlayer <= 100) {
			float zoomLevel = getWheel * 0.01f;
			distanceFromPlayer -= zoomLevel;
		} else if (distanceFromPlayer < 15 && getWheel < 0) {
			float zoomLevel = getWheel * 0.01f;
			distanceFromPlayer -= zoomLevel;
		} else if (distanceFromPlayer > 100 && getWheel > 0) {
			float zoomLevel = getWheel * 0.01f;
			distanceFromPlayer -= zoomLevel;
		} 
	}
	
	private void calculatePitch() {
		if (Mouse.isButtonDown(0)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			if (position.y > 10 && pitchChange > 0)
				pitch -= pitchChange;
			else if (position.y < 50 && pitchChange < 0)
				pitch -= pitchChange;
		} 
	}
	
	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private void checkCameraReset() {
		if (player.cameraReset) {
			angleAroundPlayer = 0;
			pitch = 20;
			player.cameraReset = false;
		}
	}
}
