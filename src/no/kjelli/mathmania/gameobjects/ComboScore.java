package no.kjelli.mathmania.gameobjects;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;

import org.newdawn.slick.Color;

public class ComboScore extends AbstractGameObject {
	private float scale = 2.0f;
	private int comboCount;
	private static final int TIME_TO_LIVE_MAX = 200;
	private int timetolive = TIME_TO_LIVE_MAX;

	public boolean fadeout = false;

	public ComboScore(float x, float y, int comboCount) {
		super(x, y, 7, Sprite.CHAR_HEIGHT);
		this.comboCount = comboCount;
		color = new Color(Color.white);
		color.a = 0.0f;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		y += 0.1;
		if (color.a < 1.0f && !fadeout) {
			color.a += 0.05;
			if (color.a == 1.0f)
				fadeout = true;
		} else
			color.a = (float) timetolive / (float) TIME_TO_LIVE_MAX;
		if (timetolive > 0)
			timetolive--;
		if (timetolive == 0)
			destroy();
	}

	@Override
	public void draw() {
		Draw.string("combo x" + comboCount, x
				- (7 + String.valueOf(comboCount).length()) * Sprite.CHAR_WIDTH
				* scale / 2, y - Sprite.CHAR_HEIGHT / 2, scale, scale, color,
				false);
	}
}
