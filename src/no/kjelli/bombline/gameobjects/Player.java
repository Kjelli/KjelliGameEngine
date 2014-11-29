package no.kjelli.bombline.gameobjects;

import static org.lwjgl.input.Keyboard.KEY_DOWN;
import static org.lwjgl.input.Keyboard.KEY_LEFT;
import static org.lwjgl.input.Keyboard.KEY_RIGHT;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.input.Keyboard.KEY_UP;
import static org.lwjgl.input.Keyboard.isKeyDown;

import java.util.ArrayList;
import java.util.Iterator;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.levels.Level;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.PacketPlayerPlaceBomb;
import no.kjelli.bombline.network.PacketPlayerUpdate;
import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

import org.newdawn.slick.Color;

public class Player extends AbstractCollidable {
	public static final int base_x = 0, base_y = 64;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = 0;
	private static final double MAX_VELOCITY = 5f;
	private static final double DECCELERATION_X = 0;
	protected static final int FRAME_DURATION = 8;
	protected static final int FRAME_COUNT = 3;
	private static final Color deadColor = new Color(0.1f, 0.1f, 0.1f);
	private static final int INVINCIBILLITY_TIMER_MAX = 100;
	private static final int FADEOUT_TIMER_MAX = 300;
	protected int walkingFrame;
	protected long steps;
	protected DIRECTION direction = DIRECTION.RIGHT;
	protected ANIMATION animation = ANIMATION.STANDING;

	protected boolean directionChange = false;
	protected boolean animationChange = false;

	private ArrayList<Bomb> bombOverlaps;
	private boolean dead = false;

	public int speed = 1;
	public int power = 2;
	public int lives = 1;
	public int invincibillity_timer = 0;
	public int fadeout_timer = FADEOUT_TIMER_MAX;
	public boolean superBomb = false;

	public Player(int x_index, int y_index) {
		super(x_index * BombermanOnline.block_size, y_index * BombermanOnline.block_size,
				12, 12);
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		sprite.setColor(new Color(Color.white));
		z = 2.0f;
		bombOverlaps = new ArrayList<Bomb>();
		tag(BombermanOnline.tag_playfield);
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
			if (invincibillity_timer == 0) {
				loseLife();
			}
		}
	}

	private void loseLife() {
		lives--;
		if (lives > 0) {
			invincibillity_timer = INVINCIBILLITY_TIMER_MAX;
		} else if (!dead) {
			fadeout_timer = FADEOUT_TIMER_MAX;
			dead = true;
			sprite.setColor(new Color(deadColor));
		}
	}

	@Override
	public void update() {
		movementLogic();
		animationLogic();
		lifeLogic();
		bombLogic();
	}

	protected void lifeLogic() {
		if (dead) {
			if (fadeout_timer > 0) {
				fadeout_timer--;
				sprite.getColor().a = Math.max(0.2f, (float) fadeout_timer
						/ FADEOUT_TIMER_MAX);
			}
		} else if (invincibillity_timer > 0) {
			invincibillity_timer--;
			sprite.getColor().a = (float) Math.abs(Math
					.cos((float) invincibillity_timer / (2 * Math.PI)));
		}
	}

	private void bombLogic() {
		bombOverlapCheck();
		if (isKeyDown(KEY_SPACE)) {
			if (bombOverlaps.isEmpty()) {
				World.add(new Bomb(getXIndex(), getYIndex(), this, power, superBomb));
				if (online())
					sendBombInfo();
			}
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

	protected static enum DIRECTION {
		LEFT(0, 96, 0, true), RIGHT(1, 96, 0, false), UP(2, 0, 0, false), DOWN(
				3, 48, 0, false);

		private int id;
		private int xOffset, yOffset;
		private boolean xflip;

		private DIRECTION(int id, int xOffset, int yOffset, boolean xflip) {
			this.id = id;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.xflip = xflip;
		}

		public static DIRECTION resolve(int id) {
			switch (id) {
			default:
			case 0:
				return LEFT;
			case 1:
				return RIGHT;
			case 2:
				return UP;
			case 3:
				return DOWN;
			}
		}

		public int getID() {
			return id;
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

	protected static enum ANIMATION {
		WALKING(0, 0, 0), STANDING(1, 0, 0);
		private int id;
		private int xOffset, yOffset;

		private ANIMATION(int id, int xOffset, int yOffset) {
			this.id = id;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}

		public int getID() {
			return id;
		}

		public int getXOffset() {
			return xOffset;
		}

		public int getYOffset() {
			return yOffset;
		}

		public static ANIMATION resolve(int id) {
			switch (id) {
			default:
			case 0:
				return WALKING;
			case 1:
				return STANDING;
			}
		}
	}

	private void movementLogic() {
		if (dead)
			return;
		if (speed > MAX_VELOCITY)
			speed = (int) MAX_VELOCITY;
		horizontalAcceleration();
		verticalAcceleration();
		if (velocity_x != 0 || velocity_y != 0) {
			z = 2.0f - y / Level.getHeight();
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

	protected void animationLogic() {
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

				if (online())
					sendInfo();
			}
		}

		if (animationChange || directionChange) {
			updateAnimation();
		}

	}

	protected void updateAnimation() {
		sprite.setTextureCoords(
				base_x + animation.getXOffset() + direction.getXOffset()
						+ walkingFrame * SPRITE_OFFSET,
				base_y + animation.getYOffset() + direction.getYOffset(),
				SPRITE_WIDTH, SPRITE_HEIGHT);
		animationChange = directionChange = false;
	}

	private boolean online() {
		return Network.client != null && Network.client.isConnected();
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
		if (online()) {
			sendInfo();
		}
	}

	public void sendInfo() {
		Network.client.sendUDP(new PacketPlayerUpdate(Network.client.getID(),
				x, y, direction.getID(), animation.getID()));
	}

	private void sendBombInfo() {
		Network.client.sendTCP(new PacketPlayerPlaceBomb(
				Network.client.getID(), getXIndex(), getYIndex()));
	}

	private void accelerateRight() {
		if (direction != DIRECTION.RIGHT) {
			direction = DIRECTION.RIGHT;
			directionChange = true;
		}
		velocity_x = speed;
	}

	private void accelerateLeft() {
		if (direction != DIRECTION.LEFT) {
			direction = DIRECTION.LEFT;
			directionChange = true;
		}
		velocity_x = -speed;
	}

	private void deccelerateX() {
		velocity_x = 0.0;
	}

	private void accelerateUp() {
		if (direction != DIRECTION.UP) {
			direction = DIRECTION.UP;
			directionChange = true;
		}
		velocity_y = speed;
	}

	private void accelerateDown() {
		if (direction != DIRECTION.DOWN) {
			direction = DIRECTION.DOWN;
			directionChange = true;
		}
		velocity_y = -speed;
	}

	private void deccelerateY() {
		velocity_y = 0;
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z, 0, 1.0f,
				1.0f, direction.getXFlip(), false, false);
		// debugDraw();
	}

	public int getXIndex() {
		return (int) ((x + width / 2) / BombermanOnline.block_size);
	}

	public int getYIndex() {
		return (int) ((y + height / 2) / BombermanOnline.block_size);
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
