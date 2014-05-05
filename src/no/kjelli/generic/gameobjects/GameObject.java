package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Drawable;

public interface GameObject extends Drawable {

	public float getX();

	public float getY();
	
	public int getXDirection();

	public int getYDirection();

	public float getCenterX();

	public float getCenterY();

	public float getWidth();

	public float getHeight();

	public double getVelocityX();

	public double getVelocityY();

	public float getSpeed();

	public boolean isVisible();

	public boolean intersects(GameObject other);

	// TODO: remove om den ikke funker
	public boolean intersects2(GameObject other);

	public boolean contains(float x, float y);

	public void update();

	public void destroy();
}
