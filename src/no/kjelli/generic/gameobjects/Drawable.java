package no.kjelli.generic.gameobjects;

import org.newdawn.slick.opengl.Texture;

public interface Drawable {
	public float getX();

	public float getY();

	public float getWidth();

	public float getHeight();

	public void draw();

	public Texture getTexture();
}
