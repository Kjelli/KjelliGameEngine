package no.kjelli.generic.gfx;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;

public class ScrollingText extends AbstractGameObject {
	private static final float scale = 1.0f;

	private String text;
	private Color color;

	public static final int HORIZONTAL = 0, VERTICAL = 1;

	public ScrollingText(String text, int direction, float speed, Color color) {
		super(determineX(text, speed, direction), determineY(text, speed,
				direction), Sprite.CHAR_WIDTH * text.length(),
				Sprite.CHAR_HEIGHT * scale);
		this.text = text;
		if (direction == HORIZONTAL)
			velocity_x = speed;
		else if (direction == VERTICAL)
			velocity_y = speed;
		this.color = new Color(color);
		z = 4;
	}

	public ScrollingText(String text) {
		this(text, VERTICAL, -1f, Sprite.DEFAULT_COLOR);
	}

	private static float determineX(String text, float speed, int direction) {
		float x = 0;
		if (direction == HORIZONTAL) {
			if (speed > 0)
				x = -Sprite.CHAR_WIDTH * scale * text.length();
			else if (speed < 0)
				x = Screen.getWidth();
		} else if (direction == VERTICAL) {
			x = Screen.getWidth() / 2
					- (Sprite.CHAR_WIDTH * text.length() * scale) / 2;
		} else {
			throw new IllegalArgumentException("Invalid direction: "
					+ direction);
		}
		return x;

	}

	private static float determineY(String text, float speed, int direction) {
		float y = 0;
		
		if (direction == HORIZONTAL) {
			y = Screen.getHeight() / 2 - Sprite.CHAR_HEIGHT * scale / 2;
		} else if (direction == VERTICAL) {
			if (speed < 0)
				y = Screen.getHeight();
			else if (speed > 0)
				y = -Sprite.CHAR_HEIGHT * scale / 2;
		} else {
			throw new IllegalArgumentException("Invalid direction: "
					+ direction);
		}
		return y;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		move();
		if (velocity_x > 0 && x > Display.getWidth()) {
			destroy();
		}
		if (velocity_x < 0 && x + width < 0) {
			destroy();
		}
		if (velocity_y > 0 && y > Display.getHeight()) {
			destroy();
		}
		if (velocity_y < 0 && y + height < 0) {
			destroy();
		}
	}

	@Override
	public void draw() {
		Draw.string(text, x, y, z, scale, scale, color, true);
	}
}
