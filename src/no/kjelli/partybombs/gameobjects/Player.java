package no.kjelli.partybombs.gameobjects;

import static org.lwjgl.input.Keyboard.*;

import java.util.ArrayList;
import java.util.Iterator;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.partybombs.Partybombs;

import org.newdawn.slick.Color;

public class Player extends AbstractCollidable {
	public static final int base_x = 0, base_y = 64;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = 0;
	private static final double MAX_VELOCITY = 1f;
	private static final double ACCELERATION = MAX_VELOCITY;
	private static final double DECCELERATION_X = 0;
	private static final int FRAME_DURATION = 8;
	private static final int FRAME_COUNT = 3;
	private static final int BOMB_COOLDOWN_MAX = 10;
	private int walkingFrame;
	private long steps;
	private DIRECTION direction = DIRECTION.RIGHT;
	private ANIMATION animation = ANIMATION.STANDING;

	private boolean directionChange = false;
	private boolean animationChange = false;

	private ArrayList<Bomb> bombOverlaps;
	private int bomb_cooldown = BOMB_COOLDOWN_MAX;
	private boolean dead = false;
	
	public Player(int x_index, int y_index) {
		super(x_index * Partybombs.block_size, y_index * Partybombs.block_size,
				12, 12);
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		sprite.setColor(new Color(Color.white));
		z = 2.0f;
		tag(Partybombs.tag_playfield);
		bombOverlaps = new ArrayList<Bomb>();
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		Collidable target = collision.getTarget();

		if (target instanceof Block || target instanceof Destructible) {
			stop(collision.getImpactDirection());
		}

		if (target instanceof Bomb) {
			if (!bombOverlaps.contains(target)) {
				stop(collision.getImpactDirection());
			}
		}
		if (target instanceof Fire) {
			dead = true;
		}
	}

	@Override
	public void update() {
		movementLogic();
		animationLogic();

		bombLogic();

		Screen.centerOn(this);
	}

	private void bombLogic() {
		bombOverlapCheck();
		if (isKeyDown(KEY_SPACE)) {
			if (bombOverlaps.isEmpty())
				World.add(new Bomb(getXIndex(), getYIndex(), this, 10));
		}
	}

	private void bombOverlapCheck() {
		Iterator<Bomb> it = bombOverlaps.iterator();
		while (it.hasNext()) {
			Bomb b = it.next();
			if (!this.intersects(b) || b.hasBlownUp()) {
				it.remove();
			}
		}
	}

	private static enum DIRECTION {
		LEFT(96, 0, true), RIGHT(96, 0, false), UP(0, 0, false), DOWN(48, 0,
				false);

		private int xOffset, yOffset;
		private boolean xflip;

		private DIRECTION(int xOffset, int yOffset, boolean xflip) {
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.xflip = xflip;
		}

		public int getXOffset() {
			return xOffset;
		}

		public int getYOffset() {
			return yOffset;
		}

		public boolean getXFlip() {
			return xflip;
		}
	}

	private static enum ANIMATION {
		WALKING(0, 0), STANDING(0, 0);

		private int xOffset, yOffset;

		private ANIMATION(int xOffset, int yOffset) {
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}

		public int getXOffset() {
			return xOffset;
		}

		public int getYOffset() {
			return yOffset;
		}
	}

	private void movementLogic() {
		horizontalAcceleration();
		verticalAcceleration();
		if (velocity_x != 0 || velocity_y != 0) {
			move();
		}
	}

	private void verticalAcceleration() {
		if (isKeyDown(KEY_UP)) {
			accelerateUp();
		} else if (isKeyDown(KEY_DOWN))
			accelerateDown();
		else
			deccelerateY();
	}

	private void horizontalAcceleration() {
		if (isKeyDown(KEY_RIGHT)) {
			accelerateRight();
		} else if (isKeyDown(KEY_LEFT))
			accelerateLeft();
		else
			deccelerateX();
	}

	private void animationLogic() {
		if (velocity_x != 0 || velocity_y != 0) {
			if (animation != ANIMATION.WALKING) {
				animation = ANIMATION.WALKING;
				animationChange = true;
			}
		} else {
			if (animation != ANIMATION.STANDING) {
				animation = ANIMATION.STANDING;
				walkingFrame = 0;
				animationChange = true;
			}
		}

		if (animationChange || directionChange) {
			sprite.setTextureCoords(
					base_x + animation.getXOffset() + direction.getXOffset()
							+ walkingFrame * SPRITE_OFFSET,
					base_y + animation.getYOffset() + direction.getYOffset(),
					SPRITE_WIDTH, SPRITE_HEIGHT);
			animationChange = directionChange = false;
		}

	}

	@Override
	public void move() {
		if (velocity_x != 0 || velocity_y != 0) {
			steps++;
			if (steps % FRAME_DURATION == 0) {
				walkingFrame = (walkingFrame + 1) % FRAME_COUNT;
				animationChange = true;
			}
		}
		super.move();
	}

	private void accelerateRight() {
		if (direction != DIRECTION.RIGHT) {
			direction = DIRECTION.RIGHT;
			directionChange = true;
		}
		if (velocity_x < MAX_VELOCITY)
			velocity_x += ACCELERATION;
		else
			velocity_x = MAX_VELOCITY;
	}

	private void accelerateLeft() {
		if (direction != DIRECTION.LEFT) {
			direction = DIRECTION.LEFT;
			directionChange = true;
		}
		if (velocity_x > -MAX_VELOCITY)
			velocity_x -= ACCELERATION;
		else
			velocity_x = -MAX_VELOCITY;
	}

	private void deccelerateX() {
		if (velocity_x > 0.1)
			velocity_x *= DECCELERATION_X;
		else if (velocity_x < -0.1)
			velocity_x *= DECCELERATION_X;
		else {
			velocity_x = 0.0;
		}
	}

	private void accelerateUp() {
		if (direction != DIRECTION.UP) {
			direction = DIRECTION.UP;
			directionChange = true;
		}
		if (velocity_y < MAX_VELOCITY)
			velocity_y += ACCELERATION;
		else
			velocity_y = MAX_VELOCITY;
	}

	private void accelerateDown() {
		if (direction != DIRECTION.DOWN) {
			direction = DIRECTION.DOWN;
			directionChange = true;
		}
		if (velocity_y > -MAX_VELOCITY)
			velocity_y -= ACCELERATION;
		else
			velocity_y = -MAX_VELOCITY;
	}

	private void deccelerateY() {
		if (velocity_y > 0.1)
			velocity_y *= DECCELERATION_X;
		else if (velocity_y < -0.1)
			velocity_y *= DECCELERATION_X;
		else {
			velocity_y = 0.0;
		}
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z, 0, 1.0f,
				1.0f, direction.getXFlip(), false, false);
		// debugDraw();
	}

	public int getXIndex() {
		return (int) ((x + width / 2) / Partybombs.block_size);
	}

	public int getYIndex() {
		return (int) ((y + height / 2) / Partybombs.block_size);
	}

	private void debugDraw() {
		Draw.string("x: " + x, 0, Screen.getHeight() - 2 * Sprite.CHAR_HEIGHT,
				1.0f, 1.0f, 1.0f, x >= 0 ? Color.green : Color.red, true);
		Draw.string("y: " + y, 0, Screen.getHeight() - 3 * Sprite.CHAR_HEIGHT,
				1.0f, 1.0f, 1.0f, y >= 0 ? Color.green : Color.red, true);
		Draw.string("vel_x: " + velocity_x, 0, Screen.getHeight() - 4
				* Sprite.CHAR_HEIGHT, 1.0f, 1.0f, 1.0f,
				velocity_x >= 0 ? Color.green : Color.red, true);
		Draw.string("vel_y: " + velocity_y, 0, Screen.getHeight() - 5
				* Sprite.CHAR_HEIGHT, 1.0f, 1.0f, 1.0f,
				velocity_y >= 0 ? Color.green : Color.red, true);
	}

	public void overlapsBomb(Bomb bomb) {
		bombOverlaps.add(bomb);
	}
}
