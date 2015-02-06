package no.kjelli.bombline.gameobjects;

import no.kjelli.bombline.BombermanOnline;
<<<<<<< HEAD
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
=======
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

public class Floor extends AbstractGameObject {
	public static final int base_x = 16, base_y = 0;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	private static final int GROW_TIMER_MAX = 200;
	private int growTimer = 0;

	public Floor(int x_index, int y_index) {
		this(x_index, y_index, false);
	}

	public Floor(int x_index, int y_index, boolean blownForth) {
		super(x_index * BombermanOnline.block_size, y_index
				* BombermanOnline.block_size, 0, 16, 16);
		if (blownForth)
			growTimer = GROW_TIMER_MAX;
<<<<<<< HEAD
		sprite = new Sprite(BombermanOnline.partybombs, 0, base_y, SPRITE_WIDTH,
=======
		sprite = new Sprite(TextureAtlas.partybombs, 0, base_y, SPRITE_WIDTH,
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
				SPRITE_HEIGHT);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (growTimer > 0)
			growTimer--;
		else {
			sprite.setTextureCoords(base_x + (int) (Math.random() * 2)
					* SPRITE_WIDTH, base_y, SPRITE_WIDTH, SPRITE_HEIGHT);
		}
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z);
	}

}
