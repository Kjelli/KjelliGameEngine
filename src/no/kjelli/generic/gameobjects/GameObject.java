package no.kjelli.generic.gameobjects;

public interface GameObject extends Drawable {

	public float getX();

	public float getY();

	public float getCenterX();

	public float getCenterY();

	public float getWidth();

	public float getHeight();

	public double getVelocityX();

	public double getVelocityY();

	public float getSpeed();

	public boolean isVisible();

	public boolean intersects(GameObject other);

	public boolean contains(float x, float y);

	public void update();

	public void destroy();
}
