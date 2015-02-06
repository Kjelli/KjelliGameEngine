package no.kjelli.generic.gameobjects;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Drawable;
import no.kjelli.generic.gfx.Sprite;

import org.lwjgl.util.Rectangle;

public abstract class AbstractGameObject implements GameObject {
	protected float x;
	protected float y;
	protected float z = 0;
	protected float width;
	protected float height;
	protected float rotation;

	protected float xScale = 1.0f;
	protected float yScale = 1.0f;

	protected Sprite sprite;

	protected double velocity_x;
	protected double velocity_y;

	protected boolean isVisible;

	private int tag;
	private boolean pause = false;

	@Deprecated
	public AbstractGameObject(float x, float y, float width, float height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public AbstractGameObject(float x, float y, float z, float width,
			float height) {
		setX(x);
		setY(y);
		setZ(z);
		setWidth(width);
		setHeight(height);
	}

	public abstract void update();

	public void move() {
		move(velocity_x, velocity_y);
	}

	public void move(double velocity_x, double velocity_y) {
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public void setRotation(float deg) {
		this.rotation = deg;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	public double getDistance(GameObject other) {
		return Math.abs(Math.hypot(this.getCenterX() - other.getCenterX(),
				this.getCenterY() - other.getCenterY()));
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
		return y + getHeight() / 2;
	}

	public float getCenterX() {
		return x + getWidth() / 2;
	}

	public double getVelocityX() {
		return velocity_x;
	}
	
	public void setVelocityX(double vel_x) {
		this.velocity_x = vel_x;
	}

	public double getVelocityY() {
		return velocity_y;
	}
	
	public void setVelocityY(double vel_y) {
		this.velocity_y = vel_y;
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

	public Sprite getSprite() {
		return sprite;
	}

	public void destroy() {
		if (sprite != null)
			sprite.destroy();
		setVisible(false);
		World.remove(this);
	}

	public boolean intersects(Rectangle bounds) {
		return this.getBounds().intersects(bounds);
	}

	@Override
	public void tag(int tag) {
		this.tag |= tag;
	}

	@Override
	public boolean hasTag(int tag) {
		return (this.tag & tag) > 0;
	}

	@Override
	public void removeTag(int tag) {
		if (hasTag(tag))
			this.tag ^= tag;
	}

	@Override
	public void removeTags() {
		this.tag = 0;
	}

	public void pause(boolean pause) {
		this.pause = pause;
	}

	public boolean isPaused() {
		return pause;
	}

	public float getXScale() {
		return xScale;
	}

	public float getYScale() {
		return yScale;
	}

	public void setXScale(float xScale) {
		this.xScale = xScale;
	}

	public void setYScale(float yScale) {
		this.yScale = yScale;
	}

	@Override
	public int compareTo(Drawable that) {
		return Float.compare(getZ(), that.getZ());
	}

}
