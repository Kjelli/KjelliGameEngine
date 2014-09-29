package no.kjelli.mathmania.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.MathMania.PLAYSTATE;
import no.kjelli.mathmania.levels.Level;
import no.kjelli.mathmania.levels.Question;

public class MultiplyBlock extends AbstractBlock {

	public static final int base_x = 0, base_y = 32;

	public MultiplyBlock(int x_index, int y_index, int difficulty) {
		super(x_index, y_index, difficulty);
		sprite = new Sprite(TextureAtlas.objects, base_x + difficulty
				* SPRITE_SIZE, base_y, SPRITE_SIZE, SPRITE_SIZE);
	}

	@Override
	public void onCollision(Collision collision) {

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public Question getQuestion() {
		return new Question(Screen.getCenterX(), Screen.getCenterY(),
				Question.TYPE.MULTIPLY, difficulty, this);
	}

}
