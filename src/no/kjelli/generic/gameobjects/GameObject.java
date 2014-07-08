package no.kjelli.generic.gameobjects;

import org.lwjgl.util.Rectangle;

public interface GameObject extends Comparable<GameObject> {

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

	public void setLayer(int layer);

	public int getLayer();

	public boolean intersects(GameObject other);

	public boolean intersects(Rectangle bounds);

	public boolean contains(float x, float y);

	public void move();
	
	public void update();
	
	public void onCreate();

	public void destroy();
}
