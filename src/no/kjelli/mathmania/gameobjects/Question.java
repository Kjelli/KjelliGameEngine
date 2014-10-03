package no.kjelli.mathmania.gameobjects;

import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.input.Keyboard.isKeyDown;

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

	public Question(TYPE type, int difficulty, Block containingBlock) {
		super(Screen.getCenterX(), Screen.getCenterY(), 0, Sprite.CHAR_HEIGHT);
		this.containingBlock = containingBlock;
		this.type = type;
		this.difficulty = difficulty;
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
		setWidth(this.toString().length() * Sprite.CHAR_WIDTH);
	}

	private void multiply() {
		term1 = (int) (Math.random() * 1.5 * difficulty + 1);
		term2 = (int) (Math.random() * 1.5 * difficulty + 1);
		ans = term1 * term2;
	}

	private void divide() {
		term2 = (int) (Math.random() * 2.5 * difficulty + 1);
		term1 = term2 * (int) (Math.random() * 10);

		ans = term1 / term2;
	}

	private void subtract() {
		do {
			term1 = (int) (Math.random() * 10 * difficulty) + 1;
			term2 = (int) (Math.random() * 10 * difficulty);
		} while (term2 > term1);
		ans = term1 - term2;
	}

	private void add() {
		term1 = (int) (Math.random() * 10 * difficulty);
		term2 = (int) (Math.random() * 10 * difficulty);
		ans = term1 + term2;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (isKeyDown(KEY_SPACE))
			MathMania.resumeGameplay();

	}

	@Override
	public void draw() {
		Draw.fillRect(Screen.getCenterX() - getWidth() / 2 - 10,
				Screen.getCenterY() - getHeight() / 2 + 40, getWidth() + 20,
				getHeight() + 20, Color.black);
		Draw.rect(Screen.getCenterX() - getWidth() / 2 - 10,
				Screen.getCenterY() - getHeight() / 2 + 40, getWidth() + 20,
				getHeight() + 20, Color.white);
		Draw.string(this.toString(), Screen.getCenterX() - getWidth() / 2,
				Screen.getCenterY() - getHeight() / 2 + 50);
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
