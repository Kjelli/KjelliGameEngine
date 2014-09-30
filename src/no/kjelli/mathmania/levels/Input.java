package no.kjelli.mathmania.levels;

import static org.lwjgl.input.Keyboard.*;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.MathMania;

public class Input extends AbstractGameObject {
	private Question question;

	private static final int COOLDOWN_MAX = 10;
	private int cooldown = 0;

	String input;

	public Input(float x, float y, Question q) {
		super(x, y, 0, Sprite.CHAR_HEIGHT);
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

		if (cooldown > 0)
			cooldown--;

		if (input.length() > 0 && isKeyDown(KEY_BACK) && cooldown == 0) {
			input = input.substring(0, input.length() - 1);
			cooldown = COOLDOWN_MAX;
			setWidth(input.length() * Sprite.CHAR_WIDTH);
			setX(x + Sprite.CHAR_WIDTH / 2);
		}

		if (isKeyDown(KEY_RETURN)) {
			if (input.isEmpty())
				MathMania.resumeGameplay();
			else if (Integer.parseInt(input) == question.getAnswer()) {
				question.getContainingBlock().destroy();
				question.getContainingBlock().getLevel().player.selection = null;
				MathMania.resumeGameplay();
				SoundPlayer.play("win");
			} else {
				MathMania.resumeGameplay();
				SoundPlayer.play("lose");
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
		setX(x - Sprite.CHAR_WIDTH / 2);
		cooldown = COOLDOWN_MAX;
	}

	@Override
	public void draw() {
		if ((MathMania.ticks % 50) > 25 && input.length() < 8)
			Draw.string(input + "_", x, y);
		else
			Draw.string(input, x, y);
	}

}
