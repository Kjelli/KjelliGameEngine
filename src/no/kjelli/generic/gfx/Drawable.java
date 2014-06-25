package no.kjelli.generic.gfx;

import no.kjelli.generic.gfx.textures.Sprite;

import org.lwjgl.util.Rectangle;

public interface Drawable {

	public float getX();

	public float getY();

	public float getWidth();

	public float getHeight();

	public void draw();

	public void setVisible(boolean visible);

	public boolean isVisible();

	public Sprite getSprite();

	public Rectangle getBounds();
}
