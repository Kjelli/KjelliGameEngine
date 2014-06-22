package no.kjelli.generic.gameobjects;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.VBO;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class AbstractGameObject implements GameObject, VBO {
	private static final int NUM_VERTICES = 6, DIMENSIONS = 3;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	protected Texture texture;
	private int vboVertexHandle;
	private int vboTexCoordHandle;

	protected Color color = new Color(Draw.DEFAULT_COLOR);

	private int layer;

	protected double velocity_x;
	protected double velocity_y;
	protected float speed;

	protected boolean isVisible;

	public AbstractGameObject(float x, float y, float width, float height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		initVBO();

	}

	private void initVBO() {
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(NUM_VERTICES
				* DIMENSIONS);
		vertexData
				.put(new float[] { x, y, 0, x + width, y, 0, x, y + height, 0,
						x + width, y + height, 0, x, y + height, 0, x + width,
						y, 0 });
		vertexData.flip();

		FloatBuffer texCoordData = BufferUtils
				.createFloatBuffer(NUM_VERTICES * 2);
		texCoordData.put(new float[] { 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1 });
		texCoordData.flip();

		vboVertexHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		vboVertexHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public abstract void update();

	public void move() {
		x += velocity_x;
		y += velocity_y;
	}

	public abstract void draw();

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getCenterY() {
		return y + height / 2;
	}

	public float getCenterX() {
		return x + width / 2;
	}

	public double getVelocityX() {
		return velocity_x;
	}

	public double getVelocityY() {
		return velocity_y;
	}

	public float getSpeed() {
		return speed;
	}

	public Color getColor() {
		return color;
	}

	private boolean valueInRange(float value, float min, float max) {
		return (value >= min) && (value <= max);
	}

	public boolean contains(float x, float y) {
		boolean xContains = valueInRange(x, getX(), getX() + getWidth());
		boolean yContains = valueInRange(y, getY(), getY() + getHeight());
		return xContains && yContains;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), (int) getWidth(),
				(int) getHeight());
	}

	public boolean intersects(GameObject other) {
		boolean xOverlap = valueInRange(getX(), other.getX(), other.getX()
				+ other.getWidth())
				|| valueInRange(other.getX(), getX(), getX() + getWidth());

		boolean yOverlap = valueInRange(getY(), other.getY(), other.getY()
				+ other.getHeight())
				|| valueInRange(other.getY(), getY(), getY() + getHeight());

		return xOverlap && yOverlap;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setLayer(int layer) {
		if (layer != World.BACKGROUND && layer != World.FOREGROUND) {
			System.err.println("INVALID LAYER");
			return;
		}
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

	public void destroy() {
		setVisible(false);
		World.remove(this);
		if (this instanceof VBO) {
			if (texture != null) {
				texture.release();
				glDeleteBuffers(vboVertexHandle);
				glDeleteBuffers(vboTexCoordHandle);
			}
		}
	}

	@Override
	public int compareTo(GameObject that) {
		return Integer.compare(this.layer, that.getLayer());
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public boolean intersects(Rectangle bounds) {
		return this.getBounds().intersects(bounds);
	}

	public int getVBOVertexHandle() {
		return vboVertexHandle;
	}

	public int getVBOTextureHandle() {
		return vboTexCoordHandle;
	}

	public int getDimensions() {
		return DIMENSIONS;
	}

	public int getVertexCount() {
		return NUM_VERTICES;
	}

}
