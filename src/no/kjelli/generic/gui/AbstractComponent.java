package no.kjelli.generic.gui;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class AbstractComponent implements GUIComponent {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected Color color = Draw.DEFAULT_COLOR;
	protected Texture texture;
	protected Texture texture_idle;
	protected Texture texture_highlighted;
	protected Texture texture_pressed;
	protected boolean visible;

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void draw() {
		if (texture != null) {
			Draw.texture(this);
			return;
		}
		Draw.fillRect(x + Screen.getX(), y + Screen.getY(), width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	private boolean valueInRange(float value, float min, float max) {
		return (value >= min) && (value <= max);
	}

	public boolean contains(float x, float y) {
		boolean xContains = valueInRange(x, getX(), getX() + getWidth());
		boolean yContains = valueInRange(y, getY(), getY() + getHeight());
		return xContains && yContains;
	}

	public abstract void update();

}
