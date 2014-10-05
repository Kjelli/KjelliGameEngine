package no.kjelli.mathmania.gameobjects;

import static org.lwjgl.input.Keyboard.KEY_0;
import static org.lwjgl.input.Keyboard.KEY_1;
import static org.lwjgl.input.Keyboard.KEY_2;
import static org.lwjgl.input.Keyboard.KEY_3;
import static org.lwjgl.input.Keyboard.KEY_4;
import static org.lwjgl.input.Keyboard.KEY_5;
import static org.lwjgl.input.Keyboard.KEY_6;
import static org.lwjgl.input.Keyboard.KEY_7;
import static org.lwjgl.input.Keyboard.KEY_8;
import static org.lwjgl.input.Keyboard.KEY_9;
import static org.lwjgl.input.Keyboard.KEY_BACK;
import static org.lwjgl.input.Keyboard.KEY_RETURN;
import static org.lwjgl.input.Keyboard.isKeyDown;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.levels.Level;

import org.newdawn.slick.Color;

public class Input extends AbstractGameObject {
	private Question question;

	private static final int COOLDOWN_MAX = 10;
	private int cooldown = 0;

	String input;

	public Input(Question q) {
		super(Screen.getCenterX(), Screen.getCenterY(), Sprite.CHAR_WIDTH,
				Sprite.CHAR_HEIGHT);
		this.question = q;
		input = new String();
	}

	public Input() {
		super(0, 0, 0, Sprite.CHAR_HEIGHT);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		checkForInputKeys();
	}

	private void checkForInputKeys() {

		if (cooldown > 0) {
			cooldown--;
			return;
		}

		if (input.length() > 0 && isKeyDown(KEY_BACK)) {
			if (input.length() < 8)
				setX(x + Sprite.CHAR_WIDTH / 2);
			input = input.substring(0, input.length() - 1);
			cooldown = COOLDOWN_MAX;
			setWidth(input.length() * Sprite.CHAR_WIDTH);
		}

		if (isKeyDown(KEY_RETURN)) {
			if (input.isEmpty())
				// Forfeit answer
				MathMania.resumeGameplay();
			else if (Integer.parseInt(input) == question.getAnswer()) {
				// Correct answer
				question.getContainingBlock().destroy();
				question.getContainingBlock().select(false);
				Level.getPlayer().selection = null;
				Combo.addToCombo(question);
				Score.addToScore(question.getDifficulty() * 10
						* (Combo.getCount() / 5 + 1));
				MathMania.resumeGameplay();
				SoundPlayer.play("right",
						((float) Combo.getCount() / 30 + 1.0f));
			} else {
				// Wrong answer
				MathMania.resumeGameplay();
				Combo.clearCombo();
				SoundPlayer.play("wrong");
			}
		}

		if (input.length() < 8) {
			if (isKeyDown(KEY_1)) {
				add("1");
			} else if (isKeyDown(KEY_2))
				add("2");
			else if (isKeyDown(KEY_3))
				add("3");
			else if (isKeyDown(KEY_4))
				add("4");
			else if (isKeyDown(KEY_5))
				add("5");
			else if (isKeyDown(KEY_6))
				add("6");
			else if (isKeyDown(KEY_7))
				add("7");
			else if (isKeyDown(KEY_8))
				add("8");
			else if (isKeyDown(KEY_9))
				add("9");
			else if (isKeyDown(KEY_0))
				add("0");
		}
	}

	private void add(String string) {
		if (cooldown > 0)
			return;
		input = input.concat(string);
		setWidth(input.length() * Sprite.CHAR_WIDTH);

		cooldown = COOLDOWN_MAX;
	}

	@Override
	public void draw() {
		if (input.isEmpty()) {
			Draw.fillRect(Screen.getWidth() / 2 - 1,
					Screen.getHeight() / 2 - 1, Sprite.CHAR_WIDTH + 1,
					Sprite.CHAR_HEIGHT + 2, 0, Color.black, true);
			Draw.rect(Screen.getWidth() / 2 - 1, Screen.getHeight() / 2 - 1,
					Sprite.CHAR_WIDTH + 1, Sprite.CHAR_HEIGHT + 2, 0,
					Color.white, true);
		} else {
			Draw.fillRect(
					Screen.getWidth()
							/ 2
							- 1
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 - 1,
					(input.length() < 8 ? Sprite.CHAR_WIDTH : 0)
							+ input.length() * Sprite.CHAR_WIDTH + 1,
					Sprite.CHAR_HEIGHT + 2, 0, Color.black, true);
			Draw.rect(
					Screen.getWidth()
							/ 2
							- 1
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 - 1,
					(input.length() < 8 ? Sprite.CHAR_WIDTH : 0)
							+ input.length() * Sprite.CHAR_WIDTH + 1,
					Sprite.CHAR_HEIGHT + 2, 0, Color.white, true);
		}
		if ((MathMania.ticks % 25) > 12 && input.length() < 8)
			Draw.string(
					input + "_",
					Screen.getWidth()
							/ 2
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 + 1, 1.0f, 1.0f, Color.white, true);
		else
			Draw.string(
					input,
					Screen.getWidth()
							/ 2
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 + 1, 1.0f, 1.0f, Color.white, true);
	}

}
