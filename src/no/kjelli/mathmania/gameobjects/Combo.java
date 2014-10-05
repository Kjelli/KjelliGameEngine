package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import sun.java2d.loops.DrawRect;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.levels.Level;

public class Combo extends AbstractGameObject {

	private static final int BOOST_PER_QUESTION = 600;
	private static final int MAX_TIMER = 3000;
	private static final int PAUSE_TIMER_MAX = 50;
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
			SoundPlayer.setGain("musicadd", getPercentage());
			SoundPlayer.setGain("musicbeat", getPercentage() * 2 - 0.5f);

		} else if (timer <= 0 && count > 0) {
			clearCombo();
		}

		if (heightModifier > 0)
			heightModifier -= 0.5f;

		targetWidth = Screen.getWidth() / 2 * getPercentage();
		if (targetWidth > topWidth)
			topWidth = targetWidth;
		drawingAcceleration = (targetWidth - drawingWidth) / 4;
		drawingWidth = drawingWidth + drawingAcceleration;
	}

	public static void addToCombo(ComboItem c) {
		count += c.getMultiplier();

		timerDelta += 0.2;
		if ((timer += BOOST_PER_QUESTION) > MAX_TIMER)
			timer = MAX_TIMER;

		pausetimer = PAUSE_TIMER_MAX;
		heightModifier = HEIGHT_MODIFIER_MAX;

		if (count % 5 == 0) {
			SoundPlayer.play("combo", 1.0f + count / 30);
			World.add(new ComboScore(Level.getPlayer().getCenterX(), Level
					.getPlayer().getCenterY() + 20, count));
		}

		Screen.shake(10, (int) (getPercentage() * 4));
	}

	public static void clearCombo() {
		if (count > 0) {
			Score.addToScore((long) Math.pow(count, 1.5) * 100);
		}
		SoundPlayer.play("comboexpire",
				0.5f + Math.max(0, Math.min((float) count / 10, 0.5f)),
				((float) count / 10));
		SoundPlayer.setGain("musicadd", 0);
		SoundPlayer.setGain("musicbeat", 0);
		topWidth = 0;
		timerDelta = 1.0f;
		timer = 0;
		count = 0;
	}

	@Override
	public void draw() {
		Draw.fillRect(Screen.getWidth() / 2 - drawingWidth
				- (timer > 0 ? topWidthIndicatorWidth / 2 : 0),
				Screen.getHeight() - HEIGHT - heightModifier, 2 * drawingWidth
						+ (timer > 0 ? topWidthIndicatorWidth : 0), height
						+ heightModifier, 0f, color, true);
		if (timer > 0) {
			Draw.fillRect(Screen.getWidth() / 2 - topWidth
					- topWidthIndicatorWidth / 2, Screen.getHeight() - HEIGHT
					- heightModifier, topWidthIndicatorWidth, height
					+ heightModifier, 0f, color, true);
			Draw.fillRect(Screen.getWidth() / 2 + topWidth
					- topWidthIndicatorWidth, Screen.getHeight() - HEIGHT
					- heightModifier, topWidthIndicatorWidth, height
					+ heightModifier, 0f, color, true);
		}
	}

	public static float getPercentage() {
		return (timer > MAX_TIMER ? 1.0f : ((float) timer / (float) MAX_TIMER));
	}

	public static int getCount() {
		return count;
	}

}
