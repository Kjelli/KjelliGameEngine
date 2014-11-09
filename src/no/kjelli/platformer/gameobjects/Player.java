package no.kjelli.platformer.gameobjects;

import static org.lwjgl.input.Keyboard.*;
import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.platformer.Platformer;
import no.kjelli.platformer.gameobjects.particles.PlayerParticle;
import no.kjelli.platformer.gravity.Gravity;

import org.newdawn.slick.Color;

public class Player extends AbstractCollidable {
	public static final int base_x = 0, base_y = 96;
	public static final int SPRITE_WIDTH = 10, SPRITE_HEIGHT = 13;
	public static final int SPRITE_OFFSET = 16;
	private static final double MAX_VELOCITY_X = 4f;
	private static final double ACCELERATION_X = MAX_VELOCITY_X / 10;
	private static final double DECCELERATION_X = 0.7f;
	private static final double JUMP_SPEED = 4f;
	private static final double JUMP_TIMER_MAX = 15;
	private static final int FRAME_DURATION = 3;
	private static final int FRAME_COUNT = 4;
	private int walkingFrame;
	private double jumpTimer;
	private long steps;
	private DIRECTION direction = DIRECTION.RIGHT;
	private ANIMATION animation = ANIMATION.STANDING;

	private MovingOnewayPlatform platform;
	private boolean onGround = false;

	private boolean directionChange = false;
	private boolean animationChange = false;

	private static float highestY;

	private static enum DIRECTION {
		LEFT(32, 0), RIGHT(0, 0);

		private int xOffset, yOffset;

		private DIRECTION(int xOffset, int yOffset) {
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

	private static enum ANIMATION {
		JUMPING(16, 0), STANDING(0, 0);

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

	public Player(float x, float y) {
		super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_WIDTH,
				SPRITE_HEIGHT);
		sprite.setColor(new Color(Color.cyan));
		z = 2.0f;
	}

	@Override
	public void onCreate() {
		setVisible(true);

	}

	@Override
	public void onCollision(Collision collision) {
		// Solid platform
		if (collision.getTarget() instanceof SolidPlatform) {
			if ((collision.getImpactDirection() & Collision.BELOW) > 0) {
				stop(Collision.BELOW);
				hitGround();
				jumpTimer = 0;
			} else if ((collision.getImpactDirection() & Collision.UP) > 0) {
				jumpTimer = 0;
				velocity_y = -1.0f;
				stop(Collision.UP);
			}
			if ((collision.getImpactDirection() & (Collision.LEFT | Collision.RIGHT)) > 0) {
				stop(Collision.LEFT | Collision.RIGHT);
			}
		}
		// Oneway Platform
		if (collision.getTarget() instanceof OnewayPlatform) {
			OnewayPlatform platform = (OnewayPlatform) collision.getTarget();
			if ((collision.getImpactDirection() & Collision.BELOW) > 0
					&& highestY >= platform.getTop() && y >= platform.getTop()) {
				stop(Collision.BELOW);
				hitGround();
				if (platform instanceof MovingOnewayPlatform) {
					MovingOnewayPlatform mv = (MovingOnewayPlatform) platform;
					this.platform = mv;
				}
			}

		}

	}

	private void hitGround() {
		onGround = true;
		velocity_y = -1.0f;
		jumpTimer = 0;
		highestY = y;
	}

	@Override
	public void update() {
		movementLogic();
		animationLogic();

		Screen.centerOn(this);
	}

	private void movementLogic() {
		if (!onGround) {
			platform = null;
			if (jumpTimer == 0) {
				fall();
			}
		}

		if (y > highestY)
			highestY = y;

		// Horizontal acceleration
		if (isKeyDown(KEY_RIGHT)) {
			accelerateRight();
		} else if (isKeyDown(KEY_LEFT))
			accelerateLeft();
		else
			deccelerate();

		// Jump - hold for higher altitude as fall() will apply gravity when
		// jumpTimer is zero
		if (isKeyDown(KEY_SPACE))
			jump();
		else if (jumpTimer > 0)
			jumpTimer = 0;

		if (velocity_x != 0
				|| velocity_y != -1.0f
				|| (platform != null && (platform.getVelocityX() != 0 || platform
						.getVelocityY() != 0))) {
			move();
		}
	}

	private void animationLogic() {
		if (onGround) {
			if (animation != ANIMATION.STANDING) {
				animation = ANIMATION.STANDING;
				animationChange = true;
			}
		} else {
			walkingFrame = 0;
			if (animation != ANIMATION.JUMPING) {
				animation = ANIMATION.JUMPING;
				animationChange = true;
			}
		}

		if (animationChange || directionChange) {
			sprite.setTextureCoords(
					base_x + animation.getXOffset() + direction.getXOffset(),
					base_y + animation.getYOffset() + direction.getYOffset()
							+ walkingFrame * SPRITE_OFFSET, SPRITE_WIDTH,
					SPRITE_HEIGHT);
			animationChange = directionChange = false;
		}

	}

	private void jump() {
		if (onGround) {
			jumpTimer = JUMP_TIMER_MAX;
			velocity_y = JUMP_SPEED + xScale / 2;
			onGround = false;
		} else if (jumpTimer > 0)
			jumpTimer--;
	}

	@Override
	public void move() {
		if (velocity_x != 0 && onGround) {
			steps++;
			if (steps % FRAME_DURATION == 0) {
				walkingFrame = (walkingFrame + 1) % FRAME_COUNT;
				animationChange = true;
			}
		}


		onGround = false;
		if (platform == null) {
			super.move();
		} else {
			super.move(velocity_x + platform.getVelocityX(), velocity_y
					+ platform.getVelocityY());
		}
		spawnParticle();
	}

	private void spawnParticle() {
		World.add(new PlayerParticle(this));
	}

	private void fall() {
		if (velocity_y > Gravity.getTerminalVelocity())
			velocity_y += Gravity.getGravitation();
	}

	private void accelerateRight() {
		if (direction != DIRECTION.RIGHT) {
			direction = DIRECTION.RIGHT;
			directionChange = true;
		}
		if (velocity_x < MAX_VELOCITY_X)
			velocity_x += ACCELERATION_X;
		else
			velocity_x = MAX_VELOCITY_X;
	}

	private void accelerateLeft() {
		if (direction != DIRECTION.LEFT) {
			direction = DIRECTION.LEFT;
			directionChange = true;
		}
		if (velocity_x > -MAX_VELOCITY_X)
			velocity_x -= ACCELERATION_X;
		else
			velocity_x = -MAX_VELOCITY_X;
	}

	private void deccelerate() {
		if (velocity_x > 0.1)
			velocity_x *= DECCELERATION_X;
		else if (velocity_x < -0.1)
			velocity_x *= DECCELERATION_X;
		else {
			velocity_x = 0.0;
			walkingFrame = 0;
			animationChange = true;
		}
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z);
		// debugDraw();
	}

	private void debugDraw() {
		Draw.string("onGround: " + onGround, 0, Screen.getHeight()
				- Sprite.CHAR_HEIGHT, 1.0f, 1.0f, 1.0f, onGround ? Color.green
				: Color.red, true);
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
		Draw.string("platform: " + platform, 0, Screen.getHeight() - 6
				* Sprite.CHAR_HEIGHT, 1.0f, 1.0f, 1.0f,
				platform == null ? Color.red : Color.green, true);
	}
}
