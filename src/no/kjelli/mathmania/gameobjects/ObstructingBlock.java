package no.kjelli.mathmania.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.levels.Level;
import no.kjelli.mathmania.levels.Question;

public class ObstructingBlock extends AbstractBlock {

	public static final int base_x = 0, base_y = 32;

	public ObstructingBlock(int x_index, int y_index, Level level) {
		super(x_index, y_index, level);
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
	public void question() {
		super.question();
	}

	@Override
	public Question getQuestion() {
		return null;
	}

	public boolean isObstructionBlock(){
		return true;
	}
	
	@Override
	public String getOperator() {
		return null;
	}
	
}
