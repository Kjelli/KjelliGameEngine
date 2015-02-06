package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import no.kjelli.generic.gfx.textures.TextureRegion;

import org.lwjgl.BufferUtils;

public class VertexBufferObject {
	public static final int TEXTURE = 1, COLOR = 2;
	public static final int RECTANGLE = 0;

	private final int dimension;
	private final int vertexCount;

	private final int vertexHandle;
	private final int textureHandle;
	// TODO:
	@SuppressWarnings("unused")
	private final int colorHandle;

	private VertexBufferObject(int vertexCount, int dimension,
			int vertexHandle, int textureHandle, int colorHandle) {
		this.vertexCount = vertexCount;
		this.dimension = dimension;
		this.vertexHandle = vertexHandle;
		this.textureHandle = textureHandle;
		this.colorHandle = colorHandle;
	}

	public static VertexBufferObject create(int template, float width,
			float height) {
		switch (template) {
		default:
		case RECTANGLE:
			return rectangle(width, height);
		}
	}

	private static VertexBufferObject rectangle(float width, float height) {
		return rectangle(width, height, 0, 1, 0, 1);
	}

	private static VertexBufferObject rectangle(float width, float height,
			float u, float u2, float v, float v2) {
		int vertices = 6;
		int dimension = 2;
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices
				* dimension);

		vertexData.put(new float[] { 0, 0, width, 0, 0, height, width, height,
				0, height, width, 0 });
		vertexData.flip();

		FloatBuffer texCoordData = BufferUtils.createFloatBuffer(vertices
				* dimension);
		texCoordData
				.put(new float[] { u, v2, u2, v2, u, v, u2, v, u, v, u2, v2 });
		texCoordData.flip();

		int vertexHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		int textureHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureHandle);
		glBufferData(GL_ARRAY_BUFFER, texCoordData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		return new VertexBufferObject(vertices, dimension, vertexHandle,
				textureHandle, -1);
	}

	public int getVertexHandle() {
		return vertexHandle;
	}

	public int getTextureHandle() {
		return textureHandle;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getDimension() {
		return dimension;
	}

	public static VertexBufferObject create(int texture_width,
			int texture_height, float u, float u2, float v, float v2) {
		return rectangle(texture_width, texture_height, u, u2, v, v2);
	}
	
	public void setTextureCoords(TextureRegion region){
		setTextureCoords(region.getU(), region.getU2(), region.getV(), region.getV2());
	}

	public void setTextureCoords(float u, float u2, float v, float v2) {
		FloatBuffer texCoordData = BufferUtils.createFloatBuffer(vertexCount
				* dimension);
		texCoordData
				.put(new float[] { u, v2, u2, v2, u, v, u2, v, u, v, u2, v2 });
		texCoordData.flip();

		glBindBuffer(GL_ARRAY_BUFFER, textureHandle);
		glBufferData(GL_ARRAY_BUFFER, texCoordData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

}
