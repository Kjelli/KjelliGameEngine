package no.kjelli.generic.gfx;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;

public class StaticText extends AbstractGameObject {
	private static final float scale = 1.0f;
	private String text;
	private Color color;

	public StaticText(String text, float x, float y, Color color) {
		super(x, y, 4f, Sprite.CHAR_WIDTH * text.length(), Sprite.CHAR_HEIGHT
				* scale);
		this.text = text;
		this.color = new Color(color);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
		Draw.string(text, x, y, z, scale, scale, color, true);
	}
}
