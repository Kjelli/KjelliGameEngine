package no.kjelli.generic.gameobjects;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Drawable;
import no.kjelli.generic.gfx.Screen;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class AbstractObject implements GameObject, Drawable {
	protected float x;
	protected float y;

	protected double velocity_x;
	protected double velocity_y;
	protected float speed;

	protected Color color = new Color(Draw.DEFAULT_COLOR);
	protected Texture texture;

	protected float width;
	protected float height;

	private int layer;
	protected boolean isVisible;

	public abstract void update();

	public void move() {
		x += velocity_x;
		y += velocity_y;
	}

	public void draw() {
		if (Screen.contains(this)) {
			if (texture == null) {
				Draw.fillRect(x, y, width, height, color);
				return;
			}
			Draw.texture(this);
		}
	}

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
		if (texture != null) {
			texture = null;
		}
	}

	@Override
	public int compareTo(GameObject that) {
		return Integer.compare(this.layer, that.getLayer());
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
