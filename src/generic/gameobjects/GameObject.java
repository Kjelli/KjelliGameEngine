package generic.gameobjects;


public interface GameObject {

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
	public void update();
	public void draw();
}
