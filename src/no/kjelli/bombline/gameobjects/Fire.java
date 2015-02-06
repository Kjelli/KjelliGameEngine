package no.kjelli.bombline.gameobjects;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.powerups.Powerup;
import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
<<<<<<< HEAD
=======
import no.kjelli.generic.gfx.textures.TextureAtlas;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

public class Fire extends AbstractCollidable {

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;
	public static final int ORIGIN = UP + DOWN + LEFT + RIGHT;

	public static final int base_x = 0, base_y = 80;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = -2;
	private static final int DECAY_MAX = 20;

	private int x_index, y_index;
	private int decay = DECAY_MAX;

	private Player source;
	private int power;
	private int direction;
	private FIRE_FRAGMENT fire_fragment;

	private boolean spread = true;
	private boolean decaying = false;
	private boolean passThrough = false;

	private static enum FIRE_FRAGMENT {
		LEFT_END(0, 0), HORIZONTAL(1, 0), ORIGIN(2, 0), RIGHT_END(3, 0), DOWN_END(
				0, 1), UP_END(1, 1), VERTICAL(2, 1), BALL(3, 1);

		private int x, y;

		FIRE_FRAGMENT(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	public Fire(int x_index, int y_index, Player source, int power,
			int direction, boolean passThrough) {
		super(x_index * BombermanOnline.block_size + BombermanOnline.block_size
				/ 2 - 6, y_index * BombermanOnline.block_size
				+ BombermanOnline.block_size / 2 - 6, 1.5f, 12, 12);
		this.x_index = x_index;
		this.y_index = y_index;
		if (power == 0) {
			switch (direction) {
			default:
			case 0:
				fire_fragment = FIRE_FRAGMENT.BALL;
				break;
			case UP:
				fire_fragment = FIRE_FRAGMENT.UP_END;
				break;
			case DOWN:
				fire_fragment = FIRE_FRAGMENT.DOWN_END;
				break;
			case LEFT:
				fire_fragment = FIRE_FRAGMENT.LEFT_END;
				break;
			case RIGHT:
				fire_fragment = FIRE_FRAGMENT.RIGHT_END;
				break;
			}
		} else {
			switch (direction) {
			case 0:
				fire_fragment = FIRE_FRAGMENT.BALL;
				break;
			case UP:
			case DOWN:
				fire_fragment = FIRE_FRAGMENT.VERTICAL;
				break;
			case LEFT:
			case RIGHT:
				fire_fragment = FIRE_FRAGMENT.HORIZONTAL;
				break;
			default:
				fire_fragment = FIRE_FRAGMENT.ORIGIN;
				break;
			}
		}
<<<<<<< HEAD
		sprite = new Sprite(BombermanOnline.partybombs, base_x
=======
		sprite = new Sprite(TextureAtlas.partybombs, base_x
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
				+ fire_fragment.getX() * SPRITE_WIDTH, base_y
				+ fire_fragment.getY() * SPRITE_HEIGHT, SPRITE_WIDTH,
				SPRITE_HEIGHT);
		this.source = source;
		this.power = power;
		this.direction = direction;
		this.passThrough = passThrough;
		tag(BombermanOnline.tag_playfield);
	}

	@Override
	public void onCreate() {
	}

	private void spreadFire() {
		if (power == 0)
			return;
		if ((direction & LEFT) > 0) {
			World.add(new Fire(x_index - 1, y_index, source, power - 1, LEFT,
					passThrough));
		}
		if ((direction & RIGHT) > 0) {
			World.add(new Fire(x_index + 1, y_index, source, power - 1, RIGHT,
					passThrough));
		}
		if ((direction & UP) > 0) {
			World.add(new Fire(x_index, y_index + 1, source, power - 1, UP,
					passThrough));
		}
		if ((direction & DOWN) > 0) {
			World.add(new Fire(x_index, y_index - 1, source, power - 1, DOWN,
					passThrough));
		}
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Block)
			spread = false;
		else if (collision.getTarget() instanceof Destructible) {
			((Destructible) collision.getTarget()).blowUp();
			spread = passThrough;
		} else if (collision.getTarget() instanceof Bomb) {
			spread = false;
			((Bomb) collision.getTarget()).blowUp();
		} else if (collision.getTarget() instanceof Powerup) {
			spread = false;
			((Powerup) collision.getTarget()).blowUp();
		}
	}

	@Override
	public void update() {
		if (!decaying) {
			Physics.getCollisions(this);
			if (spread) {
				setVisible(true);
				decaying = true;
				spreadFire();
			} else
				destroy();
		}

		if (decaying) {
			if (decay > 0)
				decay--;
			else
				destroy();
		}

	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z);
	}

}
