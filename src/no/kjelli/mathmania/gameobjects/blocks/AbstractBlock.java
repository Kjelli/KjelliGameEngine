package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Combo;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.gameobjects.particles.BlockParticle;
import no.kjelli.mathmania.levels.Level;

import org.newdawn.slick.Color;

public abstract class AbstractBlock extends AbstractCollidable implements Block {

	private boolean selected = false;
	protected Color backgroundColor = new Color(0, 0, 0, 0.0f);
	protected Color textColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	public final int x_index, y_index;
	protected final int difficulty;
	protected Level level;

	protected float highlight = 0.0f;

	public AbstractBlock(int x_index, int y_index) {
		this(x_index, y_index, -1);
	}

	public AbstractBlock(int x_index, int y_index, int difficulty) {
		super(x_index * SIZE, y_index * SIZE, SIZE, SIZE);
		this.difficulty = difficulty;
		this.x_index = x_index;
		this.y_index = y_index;

		tag(MathMania.tag_playfield);
		if (difficulty > Level.getDifficulty())
			color = new Color(Color.green);
		else
			color = new Color(Color.white);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void draw() {
		if (selected) {
			if (highlight < 1.0f) {
				highlight += 0.20;
				color.b += 0.20;
				textColor.a += 0.20;
				textColor.b += 0.20;
				backgroundColor.a += 0.20;
				backgroundColor.b += 0.20;
			}
		} else {
			if (highlight > 0.0f) {
				highlight -= 0.05;
				color.b -= 0.05;
				textColor.a -= 0.05;
				textColor.b -= 0.05;
				backgroundColor.a -= 0.05;
				backgroundColor.b -= 0.05;
			}
		}
		Draw.sprite(this, 0f, 0f, 0f, SCALE, SCALE, false);

		color.a = 0.8f + 0.2f * (float) Math.abs(Math
				.sin((float) (MathMania.ticks + x * y) / 100));

	}

	public abstract String getOperator();

	public void question() {
		MathMania.initQuestion(getQuestion());
	}

	protected abstract Question getQuestion();

	public void select(boolean select) {
		selected = select;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isObstructionBlock() {
		return false;
	}

	public void destroy() {
		super.destroy();
		int particles = 5 * Math.min(Combo.getCount(), 20);
		for (int i = 0; i < particles; i++) {
			World.add(new BlockParticle(getCenterX()
					- BlockParticle.SPRITE_SIZE / 2, getCenterY()
					- BlockParticle.SPRITE_SIZE / 2,
					(float) (i * Math.PI / (particles / 2))));
		}
	}

}
