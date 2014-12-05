package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, 
			float rz, float scale) { 											//Imports translation vector, rotational floats and scale
																				//converts to 4d transformation matrix
		Matrix4f matrix = new Matrix4f();										//Creates empty matrix
		matrix.setIdentity();													//Resets the matrix
		Matrix4f.translate(translation, matrix, matrix);						//translates, from matrix and stores it back into matrix
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix); //Rotates matrix by # of radians in first parameter
																						  //Around the x axis (2nd), of the matrix (3rd, stores into matrix (4th)
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix); 
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix); 		//(1)Scale which direction, in this case uniformly. (2-3)Take from, store into
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) { 											
		Matrix4f viewMatrix = new Matrix4f();										
		viewMatrix.setIdentity();										
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix); 																		  
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0),viewMatrix, viewMatrix); 
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
}
