package no.kjelli.partybombs.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.partybombs.Partybombs;

public class Block extends AbstractCollidable {
	public static final int base_x = 160, base_y = 80;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;

	public Block(int x_index, int y_index) {
		super(x_index * Partybombs.block_size, y_index * Partybombs.block_size,
				16, 16);
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		z = 1.0f;
		tag(Partybombs.tag_playfield);
		

	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z);
	}

}
