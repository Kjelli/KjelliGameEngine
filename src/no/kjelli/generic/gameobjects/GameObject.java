package no.kjelli.generic.gameobjects;

import no.kjelli.generic.gfx.Drawable;

import org.lwjgl.util.Rectangle;

public interface GameObject extends Drawable {

	public float getX();

	public float getY();
	
	public float getZ(); 

	public float getWidth();

	public float getHeight();

	public void setX(float x);

	public void setY(float y);
	
	public void setZ(float z);

	public void setWidth(float width);

	public void setHeight(float height);

	public void setRotation(float deg);

	public float getRotation();

	public float getCenterX();

	public float getCenterY();

	public double getVelocityX();
	public void setVelocityX(double vel_x);

	public double getVelocityY();
	public void setVelocityY(double vel_y);

	public boolean isVisible();

	public Rectangle getBounds();

	public double getDistance(GameObject other);

	public boolean intersects(GameObject other);

	public boolean intersects(Rectangle bounds);

	public boolean contains(float x, float y);

	public void move();

	public void update();

	public void onCreate();

	public void destroy();

	public void tag(int tag);

	public void pause(boolean pause);

	public boolean hasTag(int tag);

	public void removeTag(int tag);

	public void removeTags();

	public boolean isPaused();

	public void setXScale(float xScale);

	public void setYScale(float yScale);
}
