package no.kjelli.generic.gfx;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public interface Drawable {

	public float getX();

	public float getY();

	public float getWidth();

	public float getHeight();

	public void draw();

	public void setVisible(boolean visible);

	public boolean isVisible();

	public Sprite getSprite();
	
	public Color getColor();

	public Rectangle getBounds();
}
