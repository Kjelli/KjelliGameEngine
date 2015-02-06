package no.kjelli.generic;

public interface Game {

	public void init();

	public void loadSounds();

	public void render();

	public void update();

	public void destroy();
	
	public double getWidth();
	
	public double getHeight();


	public String getTitle();

}
