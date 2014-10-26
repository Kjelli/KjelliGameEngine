package no.kjelli.mathmania.gameobjects;

import static org.lwjgl.input.Keyboard.*;

import java.util.HashMap;

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

	private static final int REPEAT_COOLDOWN = 20;

	private static final HashMap<String, Integer> cooldown = new HashMap<String, Integer>();

	String input;

	public Input(Question q) {
		super(Screen.getCenterX(), Screen.getCenterY(), Sprite.CHAR_WIDTH,
				Sprite.CHAR_HEIGHT);
		this.question = q;
		input = new String();
		cooldown.clear();
		fillCooldownMap();
	}

	private void fillCooldownMap() {
		for (int i = 0; i < 10; i++)
			cooldown.put(String.valueOf(i), 0);
		cooldown.put("space", -1);
		cooldown.put("backspace", 0);
		cooldown.put("escape", 0);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		checkForInputKeys();
		for (String s : cooldown.keySet()) {
			Integer i = cooldown.get(s);
			if (i > 0)
				cooldown.put(s, i - 1);
		}
	}

	private void checkForInputKeys() {
		if (input.length() > 0 && isKeyDown(KEY_BACK)) {
			if (cooldown.get("backspace") == 0) {
				input = input.substring(0, input.length() - 1);
				setWidth(input.length() * Sprite.CHAR_WIDTH);
				cooldown.put("backspace", REPEAT_COOLDOWN);
			}
		} else if (!isKeyDown(KEY_BACK) && cooldown.get("backspace") > 0) {
			cooldown.put("backspace", 0);
		}

		if (isKeyDown(KEY_SPACE) && cooldown.get("space") == 0) {
			if (input.isEmpty())
				incorrect();
			else if (Integer.parseInt(input) == question.getAnswer()) {
				correct();
			} else {
				incorrect();
			}
		} else if (!isKeyDown(KEY_SPACE) && cooldown.get("space") == -1) {
			cooldown.put("space", 0);
		}

		if (input.length() < 8) {
			checkNumericInput();
		}
	}

	private void checkNumericInput() {
		if (isKeyDown(KEY_1))
			add("1");
		else if (cooldown.get("1") > 0)
			cooldown.put("1", 0);
		if (isKeyDown(KEY_2))
			add("2");
		else if (cooldown.get("2") > 0)
			cooldown.put("2", 0);
		if (isKeyDown(KEY_3))
			add("3");
		else if (cooldown.get("3") > 0)
			cooldown.put("3", 0);
		if (isKeyDown(KEY_4))
			add("4");
		else if (cooldown.get("4") > 0)
			cooldown.put("4", 0);
		if (isKeyDown(KEY_5))
			add("5");
		else if (cooldown.get("5") > 0)
			cooldown.put("5", 0);
		if (isKeyDown(KEY_6))
			add("6");
		else if (cooldown.get("6") > 0)
			cooldown.put("6", 0);
		if (isKeyDown(KEY_7))
			add("7");
		else if (cooldown.get("7") > 0)
			cooldown.put("7", 0);
		if (isKeyDown(KEY_8))
			add("8");
		else if (cooldown.get("8") > 0)
			cooldown.put("8", 0);
		if (isKeyDown(KEY_9))
			add("9");
		else if (cooldown.get("9") > 0)
			cooldown.put("9", 0);
		if (isKeyDown(KEY_0))
			add("0");
		else if (cooldown.get("0") > 0)
			cooldown.put("0", 0);
	}

	private void incorrect() {
		Combo.clearCombo();
		SoundPlayer.play("wrong");
		MathMania.resumeGameplay();
	}

	private void correct() {
		question.getContainingBlock().destroy();
		question.getContainingBlock().select(false);
		Level.getPlayer().selection = null;
		Combo.addToCombo(question);
		Score.addToScore(question.getDifficulty() * 10
				* (Combo.getCount() / 5 + 1));
		MathMania.resumeGameplay();
		SoundPlayer.play("right", ((float) Combo.getCount() / 30 + 1.0f));
	}

	private void add(String string) {
		if (cooldown.get(string) == 0) {
			input = input.concat(string);
			setWidth(input.length() * Sprite.CHAR_WIDTH);
			cooldown.put(string, REPEAT_COOLDOWN);
		}

	}

	@Override
	public void draw() {
		if (input.isEmpty()) {
			Draw.fillRect(Screen.getWidth() / 2 - 1,
					Screen.getHeight() / 2 - 1, 1.0f, Sprite.CHAR_WIDTH + 1,
					Sprite.CHAR_HEIGHT + 2, 0, Color.black, true);
			Draw.rect(Screen.getWidth() / 2 - 1, Screen.getHeight() / 2 - 1,
					1.1f, Sprite.CHAR_WIDTH + 1, Sprite.CHAR_HEIGHT + 2, 0,
					Color.white, true);
		} else {
			Draw.fillRect(
					Screen.getWidth()
							/ 2
							- 1
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 - 1,
					1.0f,
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
					1.1f,
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
					Screen.getHeight() / 2 + 1, 2.0f, 1.0f, 1.0f, Color.white,
					true);
		else
			Draw.string(
					input,
					Screen.getWidth()
							/ 2
							- (input.length() < 8 ? input.length() : input
									.length() - 1) * Sprite.CHAR_WIDTH / 2,
					Screen.getHeight() / 2 + 1, 2.0f, 1.0f, 1.0f, Color.white,
					true);
	}
}
