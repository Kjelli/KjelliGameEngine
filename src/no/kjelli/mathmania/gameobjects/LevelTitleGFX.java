package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;

public class LevelTitleGFX extends AbstractGameObject {
	private static final float scale = 2.0f;
	private static final int TIME_TO_LIVE_MAX = 200;

	private String title;
	private int timetolive;
	private Color color;

	public LevelTitleGFX(String title) {
		super(0, 0, Sprite.CHAR_WIDTH * title.length(), Sprite.CHAR_HEIGHT
				* scale);
		this.title = title;
		velocity_y = -0.4f;
		timetolive = TIME_TO_LIVE_MAX;
		color = new Color(Color.yellow);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		move();
		if (timetolive > 0)
			timetolive--;
		else
			destroy();

		color.a = (float) timetolive / (float) TIME_TO_LIVE_MAX;
	}

	@Override
	public void draw() {
		Draw.string(title, Screen.getWidth() / 2 - title.length()
				* Sprite.CHAR_WIDTH * scale / 2, 2 * Screen.getHeight() / 3
				- Sprite.CHAR_HEIGHT * scale / 2 + y, 3.0f, scale, scale,
				color, true);
	}

}
