package no.kjelli.pong.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;

public class Wall extends AbstractCollidable {
	public static final int FADE_TIMER = 100;
	public static int fade = 0;
	public static Color defaultColor = new Color(Color.black);
	public static Color referenceColor;
	public static Color color = defaultColor;

	public Wall(float x, float y, float width, float height) {
		super(x, y, 0.1f, width, height);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if (fade > 0) {
			color.r = ((float) fade / FADE_TIMER) * referenceColor.r;
			color.g = ((float) fade / FADE_TIMER) * referenceColor.g;
			color.b = ((float) fade / FADE_TIMER) * referenceColor.b;
			fade--;
		} else {
			color = defaultColor;
		}
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, 1f, width, height, color);
		Draw.rect(this, 1.1f);
	}

	public static void color(Color color) {
		fade = FADE_TIMER;
		referenceColor = new Color(color);
		color = referenceColor;
	}
}
