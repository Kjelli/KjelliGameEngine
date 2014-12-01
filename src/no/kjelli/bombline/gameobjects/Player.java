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
import no.kjelli.bombline.levels.LevelWrapper;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.PacketPlayerLoseLife;
import no.kjelli.bombline.network.PacketPlayerPlaceBomb;
import no.kjelli.bombline.network.PacketPlayerUpdate;
import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextFading;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.gfx.texts.TextStatic;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.input.InputListener;
import no.kjelli.mathmania.gameobjects.particles.GlitterParticle;

import org.newdawn.slick.Color;

public class Player extends AbstractCollidable {
	public static final int base_x = 0, base_y = 64;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = 0;
	protected static final int FRAME_DURATION = 8;
	protected static final int FRAME_COUNT = 3;

	protected static final int FADEOUT_TIMER_MAX = 300;
	protected static final int INVINCIBILLITY_TIMER_MAX = 100;
	protected static final int BOMB_COOLDOWN_MAX = 20;
	protected static final int BOMB_CAPACITY_INITIAL = 1;
	protected static final int LIVES_INITIAL = 3;
	protected static final int SPEED_INITIAL = 1;

	protected static final Color deadColor = new Color(0.1f, 0.1f, 0.1f);
	protected static final float DELTA_SPEED = 0.15f;
	protected int walkingFrame;
	protected long steps;
	protected DIRECTION direction = DIRECTION.DOWN;
	protected ANIMATION animation = ANIMATION.STANDING;

	protected boolean directionChange = true;
	protected boolean animationChange = false;

	private ArrayList<Bomb> bombOverlaps;
	private boolean dead = false;

	protected String name;

	protected int lives = LIVES_INITIAL;
	protected float speed = SPEED_INITIAL;
	protected int bombcapacity = BOMB_CAPACITY_INITIAL;
	protected int bombs = bombcapacity;
	protected int power = 1;
	protected int bomb_cooldown = BOMB_COOLDOWN_MAX;
	protected int invincibillity_timer = INVINCIBILLITY_TIMER_MAX;
	protected int fadeout_timer = FADEOUT_TIMER_MAX;
	protected boolean superBomb = false;

	protected TextStatic playerName;

	public Player(int x_index, int y_index) {
		super(x_index * BombermanOnline.block_size - DRAW_X_OFFSET, y_index
				* BombermanOnline.block_size - DRAW_Y_OFFSET, 2.0f, 12, 12);
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);

		// Initial call to animationLogic to make the player face downwards
		animationLogic();

		sprite.setColor(new Color(Color.white));
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
			if (Network.isHosting()) {
				loseLife();
			}
		}
	}

	public void loseLife() {
		if (invincibillity_timer > 0)
			return;
		lives--;

		if (lives > 0) {
			invincibillity_timer = INVINCIBILLITY_TIMER_MAX;
		} else {
			if (!dead) {
				fadeout_timer = FADEOUT_TIMER_MAX;
				dead = true;
			}
		}

		if (Network.isHosting()) {
			Network.getServer().sendToAllExceptTCP(Network.getClient().getID(),
					new PacketPlayerLoseLife(getID()));
		}
	}

	public int getID() {
		return Network.getClient().getID();
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
		if (dead)
			return;

		if (bomb_cooldown > 0) {
			bomb_cooldown--;
			return;
		}

		bombOverlapCheck();
		if (isKeyDown(KEY_SPACE) && getBombs() > 0) {
			if (bombOverlaps.isEmpty()) {
				World.add(new Bomb(getXIndex(), getYIndex(), this, power,
						superBomb));
				setBombs(getBombs() - 1);
				if (Network.isOnline())
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

		horizontalAcceleration();
		verticalAcceleration();
		if (velocity_x != 0 || velocity_y != 0) {
			z = 2.0f - y / LevelWrapper.getLevel().getHeight();
			move();
			playerName.setX(x + width / 2 - name.length() * Sprite.CHAR_WIDTH
					/ 2);
			playerName.setY(y + 2 * Sprite.CHAR_HEIGHT);
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

				if (Network.isOnline())
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
		if (Network.isOnline()) {
			sendInfo();
		}
	}

	public void sendInfo() {
		Network.getClient().sendUDP(
				new PacketPlayerUpdate(Network.getClient().getID(), x, y,
						direction.getID(), animation.getID()));
	}

	private void sendBombInfo() {
		Network.getClient().sendTCP(
				new PacketPlayerPlaceBomb(Network.getClient().getID(),
						getXIndex(), getYIndex()));
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

	public void setPower(int power) {
		if (power < 1)
			throw new IllegalArgumentException("Illegal bomb-power: " + power);
		this.power = power;
	}

	public int getPower() {
		return power;
	}

	public void setSuperBomb(boolean superBomb) {
		this.superBomb = superBomb;
	}

	public boolean hasSuperBomb() {
		return superBomb;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public int getBombCapacity() {
		return bombcapacity;
	}

	public void setBombCapacity(int bombcapacity) {
		this.bombcapacity = bombcapacity;
	}

	public void debug_highlight() {
		for (int count = 0; count < 20; count++) {
			World.add(new GlitterParticle(x, y, 4.0f, this));
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void increaseSpeed() {
		speed += DELTA_SPEED;
	}

	public void setName(String name) {
		this.name = name;

		if (playerName != null) {
			playerName.setText(name);
			playerName.setX(x + width / 2 - name.length() * Sprite.CHAR_WIDTH
					/ 2);
			playerName.setY(y + 2 * Sprite.CHAR_HEIGHT);
		} else {
			playerName = new TextStatic(getName(), getX() + getWidth() / 2
					- name.length() * Sprite.CHAR_WIDTH / 2, getY() + 2
					* Sprite.CHAR_HEIGHT, Color.white, false);
			World.add(playerName);
		}
	}

	public String getName() {
		return name;
	}

	public void displayName(boolean display) {
		playerName.setVisible(display);
	}
	
	@Override
	public void destroy() {
		playerName.destroy();
		super.destroy();
	}

}
