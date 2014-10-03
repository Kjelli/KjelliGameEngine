package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import sun.java2d.loops.DrawRect;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.mathmania.levels.Level;

public class Combo extends AbstractGameObject {

	private static final int BOOST_PER_QUESTION = 600;
	private static final int MAX_TIMER = 3000;
	private static final int PAUSE_TIMER_MAX = 30;
	private static int count;
	private static int timer;
	private static int pausetimer;
	private static float timerDelta;

	private static float topWidth;
	private static float topWidthIndicatorWidth = 1;

	private static float drawingWidth;
	private static float targetWidth;

	private static final float HEIGHT = 20;
	private static final float HEIGHT_MODIFIER_MAX = 5;
	private static float heightModifier;

	public static float drawingAcceleration;

	public Combo() {
		super(0, Screen.getHeight() - HEIGHT, Screen.getWidth(), HEIGHT);
		color = new Color(Color.white);
		color.a = 0.9f;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (timer > 0) {
			if (pausetimer > 0) {
				pausetimer--;
			} else {
				timer -= timerDelta;
			}
			color.g = Math.max(getPercentage(), heightModifier
					/ HEIGHT_MODIFIER_MAX);
			color.b = Math.max(getPercentage(), heightModifier
					/ HEIGHT_MODIFIER_MAX);
		} else if (timer <= 0 && count > 0) {
			timer = 0;
			clearCombo();
		}

		if (heightModifier > 0)
			heightModifier -= 0.5f;

		targetWidth = Screen.getWidth() * getPercentage();
		if (targetWidth > topWidth)
			topWidth = targetWidth;
		drawingAcceleration = (targetWidth - drawingWidth) / 10;
		drawingWidth = drawingWidth + drawingAcceleration;
	}

	public static void addToCombo(ComboItem c) {
		timerDelta += 0.2;
		timer += BOOST_PER_QUESTION;
		pausetimer = PAUSE_TIMER_MAX;
		count += c.getMultiplier();
		heightModifier = HEIGHT_MODIFIER_MAX;

		if (count % 5 == 0) {
			World.add(new ComboScore(Level.getPlayer().getCenterX(), Level
					.getPlayer().getCenterY() + 20, count));
		}

		Screen.shake(10, (int) (getPercentage() * 10));
	}

	public static void clearCombo() {
		if (count > 0) {
			Score.addToScore(count * 100);
		}
		topWidth = 0;
		timerDelta = 1.0f;
		timer = 0;
		count = 0;
		// TODO: score
	}

	@Override
	public void draw() {
		Draw.fillRect(0, Screen.getHeight() - HEIGHT - heightModifier,
				drawingWidth, height + heightModifier, 0f, color, true);
		Draw.fillRect(topWidth - topWidthIndicatorWidth, Screen.getHeight()
				- HEIGHT - heightModifier, topWidthIndicatorWidth, height
				+ heightModifier, 0f, color, true);
	}

	public static float getPercentage() {
		return (timer > MAX_TIMER ? 1.0f : ((float) timer / (float) MAX_TIMER));
	}

	public static int getCount() {
		return count;
	}

}
