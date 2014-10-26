package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.gameobjects.Question.TYPE;
import no.kjelli.mathmania.levels.Level;

public class AddBlock extends AbstractBlock {

	public static final int base_x = 96, base_y = 32;

	public AddBlock(int x_index, int y_index, int difficulty) {
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
		return new Question(Question.TYPE.ADD, difficulty, this);
	}

	@Override
	public String getOperator() {
		return "-";
	}

}
