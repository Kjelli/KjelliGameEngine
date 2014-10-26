package no.kjelli.mathmania.levels;

import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.input.Keyboard.isKeyDown;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Block;

public class Question extends AbstractGameObject {
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

	public Question(float x, float y, TYPE type, int difficulty, Block containingBlock) {
		super(x, y, 0, Sprite.CHAR_HEIGHT);
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
		term1 = (int) (Math.random() * 2.5 * difficulty + 1);
		term2 = (int) (Math.random() * 2.5 * difficulty + 1);
		ans = term1 * term2;
	}

	private void divide() {
		term2 = (int) (Math.random() * 2.5 * difficulty + 1);
		term1 = term2 * (int) (Math.random() * 5);

		ans = term1 / term2;
	}

	private void subtract() {
		do {
			term1 = (int) (Math.random() * 10 * difficulty) + 1;
			term2 = (int) (Math.random() * 10 * difficulty);
		} while (term2 > term1 && difficulty <= TRESHOLD_ONE);
		// Difficulty below treshold one will contain a - b where a >= b
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
		Draw.string(this.toString(), x - getWidth()/2, y - getHeight()/2 + 100);
	}

	public String toString() {
		return "What is " + term1 + " " + operator() + " " + term2 + "?";
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
	
	public Block getContainingBlock(){
		return containingBlock;
	}

	// public static void main(String[] args) {
	// for (;;) {
	// Question q = new Question(TYPE.ADD, 1);
	// System.out.println(q);
	// }
	// }

}
