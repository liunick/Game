package models;

public class RawModel {
	
	private int vaoID; //Each VAO has an ID
	private int vertexCount; //Number of verticies
	
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

}
