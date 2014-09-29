package no.kjelli.mathmania.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.levels.Question;

public class DivideBlock extends AbstractBlock {

	public static final int base_x = 0, base_y = 32;

	public DivideBlock(int x_index, int y_index, int difficulty) {
		super(x_index, y_index, difficulty);
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
		color = color.darker();
	}

	@Override
	public void onCollision(Collision collision) {

	}

	@Override
	public void update() {
	}

	@Override
	public Question getQuestion() {
		return new Question(Screen.getCenterX(), Screen.getCenterY(),
				Question.TYPE.DIVIDE, difficulty, this);
	}

	@Override
	public String getOperator() {
		return "/";
	}

}
