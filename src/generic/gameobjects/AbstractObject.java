package generic.gameobjects;

import generic.Draw;

import org.newdawn.slick.Color;

public abstract class AbstractObject implements GameObject {
	protected float x;
	protected float y;
	protected double velocity_x;
	protected double velocity_y;

	protected Color color = Draw.DEFAULT_COLOR;

	protected float width;
	protected float height;

	protected boolean isVisible;

	public abstract void update();

	public void draw() {
		Draw.fillRect(x, y, width, height, color);
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

	boolean valueInRange(float value, float min, float max) {
		return (value >= min) && (value <= max);
	}

	public boolean intersects(GameObject other) {
		boolean xOverlap = valueInRange(this.x, other.getX(), other.getX()
				+ other.getWidth())
				|| valueInRange(other.getX(), getX(), getX() + getWidth());

		boolean yOverlap = valueInRange(this.y, other.getY(), other.getY()
				+ other.getHeight())
				|| valueInRange(other.getY(), getY(), getY() + getHeight());

		return xOverlap && yOverlap;
	}

}
