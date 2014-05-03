package generic.gameobjects;


public interface GameObject {

	public void setX(float x);
	public void setY(float y);

	public float getX();
	public float getY();
	
	public float getCenterX();
	public float getCenterY();

	public float getWidth();
	public float getHeight();
	
	public boolean isVisible();

	public boolean intersects(GameObject other);
	public void update();
	public void draw();
}
