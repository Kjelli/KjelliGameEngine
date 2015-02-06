package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.blocks.Block;

public class Question extends AbstractGameObject implements ComboItem {
	public static final int MIN_DIFFICULTY = 0;
	public static final int MAX_DIFFICULTY = 100;
	public static final int TRESHOLD_ONE = (MAX_DIFFICULTY - MIN_DIFFICULTY) / 3;

	public static enum TYPE {
		MULTIPLY, DIVIDE, ADD, SUBTRACT
	}

	public final TYPE type;
	private int term1, term2, ans;
	private final int difficulty;
	private Block containingBlock;
	private final boolean encrypted;
	private String scrambledString;

	public Question(TYPE type, int difficulty, boolean encrypted,
			Block containingBlock) {
		super(Screen.getCenterX(), Screen.getCenterY(), 0, Sprite.CHAR_HEIGHT);
		this.containingBlock = containingBlock;
		this.type = type;
		this.difficulty = difficulty;
		this.encrypted = encrypted;
		switch (type) {
		case ADD:
			add();
			break;
		case SUBTRACT:
			subtract();
			break;
		case MULTIPLY:
			multiply();
			break;
		case DIVIDE:
			divide();
			break;
		}
		if (encrypted) {
			scrambledString = scramble(this.toString());
		}
		setWidth(this.toString().length() * Sprite.CHAR_WIDTH);
	}

	private void multiply() {
		term1 = (int) (Math.random() * 1.5 * difficulty + 1);
		term2 = (int) (Math.random() * 1.5 * difficulty + 1);
		ans = term1 * term2;
	}

	private void divide() {
		term2 = (int) ((Math.random()) * 2.5 * difficulty + 1);
		term1 = term2 * (int) ((Math.random()) * difficulty * 5);

		ans = term1 / term2;
	}

	private void subtract() {
		do {
			term1 = (int) ((Math.random() + 1) * 2 * difficulty);
			term2 = (int) ((Math.random() + 1) * 2 * difficulty);
		} while (term2 > term1);
		ans = term1 - term2;
	}

	private void add() {
		term1 = (int) ((Math.random() + 1) * 2 * difficulty);
		term2 = (int) ((Math.random() + 1) * 2 * difficulty);
		ans = term1 + term2;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (MathMania.ticks % 25 == 0) {
			scrambledString = scramble(this.toString());
		}
	}

	@Override
	public void draw() {
		Draw.fillRect(Screen.getCenterX() - getWidth() / 2 - 10,
				Screen.getCenterY() - getHeight() / 2 + 41, 1.0f, getWidth() + 19,
				getHeight() + 19, 0.0f, Color.black, false);
		Draw.rect(Screen.getCenterX() - getWidth() / 2 - 10,
				Screen.getCenterY() - getHeight() / 2 + 40, 1.1f,
				getWidth() + 20, getHeight() + 20, 0.0f, Color.white, false);
		if (!encrypted)
			Draw.string(this.toString(), Screen.getCenterX() - getWidth() / 2,
					Screen.getCenterY() - getHeight() / 2 + 50, 2.0f);
		if (encrypted)
			Draw.string(scrambledString, Screen.getCenterX() - getWidth() / 2,
					Screen.getCenterY() - getHeight() / 2 + 50, 2.0f);
	}

	private String scramble(String string) {
		StringBuilder sb = new StringBuilder();
		for (char c : this.toString().toCharArray()) {
			if (Math.random() > 0.2f) {
				sb.append((char) (48 + Math.random() * (42)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public String toString() {
		return term1 + " " + operator() + " " + term2;
	}

	private String operator() {
		switch (type) {
		case ADD:
			return "plus";
		case SUBTRACT:
			return "minus";
		case MULTIPLY:
			return "times";
		case DIVIDE:
			return "divided by";
		default:
			return "?";
		}
	}

	public int getAnswer() {
		return ans;
	}

	public Block getContainingBlock() {
		return containingBlock;
	}

	public long getDifficulty() {
		return difficulty;
	}

	@Override
	public int getScore() {
		return difficulty * 10;
	}

	@Override
	public int getMultiplier() {
		return 1;
	}

}
