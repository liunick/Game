package entities;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class Entity2D {

	private float entityWidth;
	private float entityLength;
	private float xPos, yPos;
	
	public Entity2D(int xPos, int yPos, int entityLength, int entityWidth) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.entityLength = entityLength;
		this.entityWidth = entityWidth;
	}
	
	public void draw() {
		glRectd(xPos, yPos, xPos + entityWidth, yPos + entityLength);
	}
	
	public void update(int delta) {
		
	}
	
	public void setLocation(float x, float y) {
		
	}

	public float getEntityWidth() {
		return entityWidth;
	}

	public void setEntityWidth(float entityWidth) {
		this.entityWidth = entityWidth;
	}

	public float getEntityLength() {
		return entityLength;
	}

	public void setEntityLength(float entityLength) {
		this.entityLength = entityLength;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

}
