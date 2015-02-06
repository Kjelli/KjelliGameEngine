package no.kjelli.generic.gfx.texts;

import org.newdawn.slick.Color;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;

public class TextFading extends AbstractGameObject {
	private static final float scale = 1.0f;
	private String text;
	private Color color;
	private int timeToLiveMax;
	private int timeToLive;

	public TextFading(String text, float x, float y, Color color, int timeToLive) {
		super(x, y, 4f, Sprite.CHAR_WIDTH * text.length(), Sprite.CHAR_HEIGHT
				* scale);
		this.timeToLiveMax = this.timeToLive = timeToLive;
		this.text = text;
		this.color = new Color(color);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (timeToLive > 0) {
			timeToLive--;
			color.a = (float) timeToLive / timeToLiveMax;
		} else {
			destroy();
		}
	}

	@Override
	public void draw() {
		Draw.string(text, x, y, z, scale, scale, color, false);
	}
}
