package no.kjelli.mathmania.gameobjects;

import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.levels.Question;

import org.newdawn.slick.Color;

public abstract class AbstractBlock extends AbstractCollidable implements Block {

	private boolean selected = false;

	public final int x_index, y_index;
	protected final int difficulty;

	public AbstractBlock(int x_index, int y_index) {
		this(x_index, y_index, -1);
	}

	public AbstractBlock(int x_index, int y_index, int difficulty) {
		super(x_index * SIZE, y_index * SIZE, SIZE, SIZE);
		this.difficulty = difficulty;
		tag(MathMania.tag_playfield);
		this.x_index = x_index;
		this.y_index = y_index;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void draw() {
		if (selected) {
			if (color.g < 1.0f)
				color.g += 0.20;
		} else {
			if (color.g > 0.0f)
				color.g -= 0.10;
		}
		Draw.sprite(this, 0f, 0f, 0f, SCALE, SCALE, false);
	}

	public void question() {
		MathMania.initQuestion(getQuestion());
	}

	public abstract Question getQuestion();

	public void select(boolean select) {
		selected = select;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isObstructionBlock() {
		return false;
	}

}
