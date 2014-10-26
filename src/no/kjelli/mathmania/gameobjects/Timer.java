package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;

public class Timer extends AbstractGameObject {
	public static float scale = 1.0f;

	private long start;
	private long running;

	private int min, sec, millis;

	private boolean counting = false;

	public Timer() {
		super(Screen.getCenterX(), Screen.getHeight(), Sprite.CHAR_WIDTH * 8
				* scale, Sprite.CHAR_HEIGHT * scale);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	public void start() {
		start = System.currentTimeMillis();
		counting = true;
	}

	public void stop() {
		counting = false;
	}

	@Override
	public void update() {
		if (counting)
			running = System.currentTimeMillis() - start;

		min = (int) (running / 60000) % 60;
		sec = (int) (running / 1000) % 60;
		millis = (int) (running / 10) % 100;
	}

	@Override
	public void draw() {
		Draw.string("Time", Screen.getWidth() / 2 - Sprite.CHAR_WIDTH * scale
				* 4 / 2, Screen.getHeight() - 4 * Sprite.CHAR_HEIGHT * scale,
				scale, scale, Color.yellow, true);
		Draw.string((min < 10 ? "0" + min : min) + ":"
				+ (sec < 10 ? "0" + sec : sec) + ":"
				+ (millis < 10 ? "0" + millis : millis), Screen.getWidth() / 2
				- Sprite.CHAR_WIDTH * Timer.scale * 8 / 2, Screen.getHeight()
				- 5 * Sprite.CHAR_HEIGHT * Timer.scale, scale, scale,
				Color.white, true);
	}
}
