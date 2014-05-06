package no.kjelli.generic.gameobjects;

import no.kjelli.generic.*;

import org.newdawn.slick.Color;

public abstract class AbstractObject implements GameObject, Drawable {
	protected float x;
	protected float y;
	protected float xStep;
	protected float yStep;

	protected double velocity_x;
	protected double velocity_y;
	protected float speed;

	protected Color color = Draw.DEFAULT_COLOR;

	protected float width;
	protected float height;

	protected boolean isVisible;

	public abstract void update();

	public void draw() {
		if (Screen.contains(this)) {
			Draw.fillRect(x, y, width, height, color);
			Draw.rect(x, y, width, height, Color.cyan);
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

	public void nudge(Collision collision) {
		System.out.println("NUDGE");
		int direction = collision.getImpactDirection();

		if ((direction & Collision.ABOVE) > 0)
			setY(y - 1);
		if ((direction & Collision.BELOW) > 0)
			setY(y + 1);
		if ((direction & Collision.LEFT) > 0)
			setX(x + 1);
		if ((direction & Collision.RIGHT) > 0)
			setX(x - 1);

	}

	private boolean valueInRange(float value, float min, float max) {
		return (value >= min) && (value <= max);
	}

	public boolean contains(float x, float y) {
		boolean xContains = valueInRange(x, getX(), getX() + getWidth());
		boolean yContains = valueInRange(y, getY(), getY() + getHeight());
		return xContains && yContains;
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

	public int getXDirection() {
		int xadd;
		if (getVelocityX() > 0)
			xadd = 5;
		else if (getVelocityX() < 0)
			xadd = -5;
		else
			xadd = 0;
		return xadd;
	}

	public int getYDirection() {
		int yadd;
		if (getVelocityY() > 0)
			yadd = 5;
		else if (getVelocityY() < 0)
			yadd = -5;
		else
			yadd = 0;
		return yadd;
	}

	public void destroy() {
		setVisible(false);
		World.remove(this);
	}

}
