package no.kjelli.mathmania.gameobjects.blocks;

import org.newdawn.slick.Color;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.levels.Level;

public class ObstructingBlock extends AbstractBlock {

	public static final int base_x = 0, base_y = 32;

	public ObstructingBlock(int x_index, int y_index) {
		super(x_index, y_index);
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
		if (x_index == 0 || x_index == Level.getWidth() / Block.SIZE - 1
				|| y_index == 0
				|| y_index == Level.getHeight() / Block.SIZE - 1)
			sprite.setColor(Color.gray);
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

	public boolean isObstructionBlock() {
		return true;
	}

	@Override
	public String getOperator() {
		return null;
	}

}
