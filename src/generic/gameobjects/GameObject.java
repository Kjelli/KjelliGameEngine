package generic.gameobjects;

import generic.Draw;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float velocity_x;
	protected float velocity_y;

	protected Color color = Draw.DEFAULT_COLOR;

	protected float width;
	protected float height;

	protected boolean isVisible;

	public abstract void update();

	public abstract void onCollision(GameObject go);

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

	public boolean intersects(GameObject other) {
		boolean intersects;
		intersects = (new Rectangle((int) x, (int) y, (int) width, (int) height)
				.intersects(new Rectangle((int) other.x, (int) other.y,
						(int) other.width, (int) other.height)));
		return intersects;
	}
}
