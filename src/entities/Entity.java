package entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {

	private TexturedModel model;
	private Vector3f position3D;
	private Vector2f position2D;
	private float rotX, rotY, rotZ;
	private float scale;
	
	public boolean is3D = false;
	
	
	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		is3D = true;
		this.model = model;
		this.position3D = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	/*
	public Entity(TexturedModel model, Vector2f position, float rotX, float rotY, float rotZ, float scale) {
		is3D = false;
		GL30.glBindVertexArray(model.getRawModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);;
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getRawModel().getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		this.model = model;
		this.setPosition2D(position);
		this.rotX = rotX;
		this.rotY = rotY;
		this.scale = scale;
	}
*/
	public void increasePosition(float dx, float dy, float dz) {
		this.position3D.x += dx;
		this.position3D.y += dy;
		this.position3D.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	/*
	public void increasePosition(float dx, float dy) {
		this.position3D.x += dx;
		this.position3D.y += dy;
	}
	
	public void increaseRotation(float dx, float dy) {
		this.rotX += dx;
		this.rotY += dy;
	}
*/
	public TexturedModel getModel() {
		return model;
	}


	public void setModel(TexturedModel model) {
		this.model = model;
	}


	public Vector3f getPosition() {
		return position3D;
	}


	public void setPosition(Vector3f position) {
		this.position3D = position;
	}


	public float getRotX() {
		return rotX;
	}


	public void setRotX(float rotX) {
		this.rotX = rotX;
	}


	public float getRotY() {
		return rotY;
	}


	public void setRotY(float rotY) {
		this.rotY = rotY;
	}


	public float getRotZ() {
		return rotZ;
	}


	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}


	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
/*
	public Vector2f getPosition2D() {
		return position2D;
	}

	public void setPosition2D(Vector2f position2d) {
		position2D = position2d;
	}
	*/
	

}
