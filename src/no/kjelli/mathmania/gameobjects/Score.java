package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;

public class Score extends AbstractGameObject {
	public static float scale = 1f;

	private static long score = 0;

	public Score() {
		super(Screen.getWidth() / 2, Screen.getHeight(), Sprite.CHAR_WIDTH
				* scale, Sprite.CHAR_HEIGHT * scale);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public static void addToScore(long score) {
		setScore(Score.score + score);
	}

	public static void setScore(long score) {
		Score.score = score;
	}

	public static void clearScore() {
		setScore(0);
	}

	@Override
	public void draw() {
		Draw.string("SCORE", Screen.getWidth() / 2 - Sprite.CHAR_WIDTH * scale
				* 5 / 2, Screen.getHeight() - 8 * Sprite.CHAR_HEIGHT * scale,
				scale, scale, Color.yellow, true);
		Draw.string("" + score, Screen.getWidth() / 2
				- String.valueOf(score).length() * Sprite.CHAR_WIDTH * scale
				/ 2, Screen.getHeight() - 9 * Sprite.CHAR_HEIGHT * scale,
				scale, scale, Color.white, true);
		if (Combo.getCount() > 0) {
			Draw.string("COMBO", Screen.getWidth() / 2 - Sprite.CHAR_WIDTH * 5
					* scale / 2, Screen.getHeight() - 11 * Sprite.CHAR_HEIGHT
					* scale, scale, scale, Color.yellow, true);
			Draw.string("" + Combo.getCount(), Screen.getWidth() / 2
					- String.valueOf(Combo.getCount()).length()
					* Sprite.CHAR_WIDTH * scale / 2, Screen.getHeight() - 12
					* Sprite.CHAR_HEIGHT * scale, scale, scale, Color.white,
					true);
		}
	}
}
