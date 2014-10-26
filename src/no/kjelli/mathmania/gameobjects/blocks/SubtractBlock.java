package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.gameobjects.Question.TYPE;
import no.kjelli.mathmania.levels.Level;

public class SubtractBlock extends AbstractBlock {

	public static final int base_x = 128, base_y = 32;

	public SubtractBlock(int x_index, int y_index, int difficulty, int encryptKey) {
		super(x_index, y_index, difficulty, encryptKey);
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
	}

	@Override
	public void onCollision(Collision collision) {

	}

	@Override
	public void update() {
	}

	@Override
	public Question getQuestion() {
		return new Question(Question.TYPE.SUBTRACT, difficulty, isEncrypted(), this);
	}

	@Override
	public String getOperator() {
		return "+";
	}

}
