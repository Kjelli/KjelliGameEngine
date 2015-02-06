package no.kjelli.generic.gfx.texts;

import org.newdawn.slick.Color;

public class TextFloating extends TextStatic {
	float base_y;

	public TextFloating(String text, float x, float y, Color color) {
		this(text, x, y, color, true);
	}

	public TextFloating(String text, float x, float y, Color color,
			boolean followScreen) {
		super(text, x, y, color, followScreen);
		base_y = y;
	}

	@Override
	public void update() {
		setY(base_y + (float) Math.sin(0 / (20 * Math.PI)) * 4);
		super.update();
	}

}
