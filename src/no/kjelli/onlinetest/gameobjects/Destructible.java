package no.kjelli.onlinetest.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.onlinetest.OnlineTest;
import no.kjelli.onlinetest.levels.Level;

public class Destructible extends AbstractCollidable {
	public static final int base_x = 144, base_y = 0;
	public static final int blow_x = 224, blow_y = 48;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	private static final int BLOW_TIMER_MAX = 4;

	public int blow_frame = 3;
	public int blow_timer = BLOW_TIMER_MAX;

	public boolean destroying = false;
	public int x_index, y_index;

	public Destructible(int x_index, int y_index) {
		super(x_index * OnlineTest.block_size, y_index * OnlineTest.block_size,
				16, 16);
		this.x_index = x_index;
		this.y_index = y_index;
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		z = 0.0f;
		tag(OnlineTest.tag_playfield);
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
		if (destroying) {
			if (blow_timer > 0) {
				blow_timer--;
			} else {
				blow_timer = BLOW_TIMER_MAX;
				if (blow_frame > 0) {
					blow_frame--;
					sprite.setTextureCoords(blow_x, blow_y + blow_frame
							* SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
				} else
					destroy();
			}
		}
	}

	public void blowUp() {
		if (!destroying) {
			destroying = true;
			Floor floor = new Floor(x_index, y_index, true);
			Level.addGameObject(floor);
			World.add(floor);
			sprite.setTextureCoords(blow_x, blow_y, SPRITE_WIDTH, SPRITE_HEIGHT);
		}
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y);

	}

}
