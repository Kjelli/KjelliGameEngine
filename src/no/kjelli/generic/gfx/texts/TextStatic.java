package no.kjelli.generic.gfx.texts;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;

public class TextStatic extends AbstractGameObject {
	private static final float scale = 1.0f;
	private String text;
	private Color color;
	private boolean followScreen;

	public TextStatic(String text, float x, float y, Color color) {
		this(text, x, y, color, true);
	}

	public TextStatic(String text, float x, float y, Color color,
			boolean followScreen) {
		super(x, y, 4f, Sprite.CHAR_WIDTH * text.length(), Sprite.CHAR_HEIGHT
				* scale);
		this.text = text;
		this.followScreen = followScreen;
		this.color = new Color(color);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
	}

	public void setText(String newText) {
		if (newText != null)
			text = newText;
		else
			text = "";
	}

	@Override
	public void draw() {
		Draw.string(text, x, y, z, scale, scale, color, followScreen);
	}
}
