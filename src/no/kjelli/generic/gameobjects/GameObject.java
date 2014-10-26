package no.kjelli.generic.gameobjects;

import no.kjelli.generic.gfx.Drawable;

import org.lwjgl.util.Rectangle;

public interface GameObject extends Drawable, Comparable<GameObject> {

	public float getX();

	public float getY();

	public float getCenterX();

	public float getCenterY();

	public float getWidth();

	public float getHeight();

	public double getVelocityX();

	public double getVelocityY();

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
}
