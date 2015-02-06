package no.kjelli.bombline.gameobjects;

<<<<<<< HEAD
import no.kjelli.bombline.BombermanOnline;
=======
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Fire;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import no.kjelli.bombline.levels.LevelWrapper;
import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
<<<<<<< HEAD

import org.newdawn.slick.Color;
=======
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.sound.SoundPlayer;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

public class Bomb extends AbstractCollidable {

	public static final int base_x = 64, base_y = 80;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int SPRITE_OFFSET = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = -2;
	public static final int BLOWUP_FRAME_INCREMENT = 20;
	public static final int BLOWUP_FRAMES = 6;

	public int x_index, y_index;
	public Player source;
	public int power;
	public boolean isSuper = false;

	private long ticks = 0;
	private int blowUpTimer = 0;
	private boolean hasBlownUp = false;
	private boolean newlyCreated = false;
	private boolean ticking;

	public Bomb(int x_index, int y_index, Player source, int power,
			boolean isSuper) {
		this(x_index, y_index, source, power, isSuper, true);
	}

	public Bomb(int x_index, int y_index, Player source, int power,
			boolean isSuper, boolean ticking) {
		super(x_index * BombermanOnline.block_size + BombermanOnline.block_size
				/ 2 - 6, y_index * BombermanOnline.block_size
				+ BombermanOnline.block_size / 2 - 6, -1f, 12, 12);
		this.x_index = x_index;
		this.y_index = y_index;
		this.source = source;
		this.power = power;
		this.ticking = ticking;
		this.isSuper = isSuper;
<<<<<<< HEAD
		sprite = new Sprite(BombermanOnline.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		if (LevelWrapper.getLevel() != null) {
			z = 2.0f - y / LevelWrapper.getLevel().getHeight();
		}
=======
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);

		// Used in bomb particle objects, which are used before levels are
		// initialized
		if (LevelWrapper.getLevel() != null)
			z = (float) (2.0f - y / LevelWrapper.getLevel().getHeight());
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		tag(BombermanOnline.tag_playfield);

		if (isSuper)
			sprite.setColor(new Color(Color.red));
	}

	@Override
	public void onCreate() {
		newlyCreated = true;
		Physics.getCollisions(this);
		newlyCreated = false;
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Player) {
			if (newlyCreated)
				((Player) collision.getTarget()).overlapsBomb(this);
		}
	}

	@Override
	public void update() {
		if (!ticking)
			return;
		ticks++;
		if (ticks % BLOWUP_FRAME_INCREMENT == 0) {
			blowUpTimer++;
			sprite.setTextureCoords(base_x + blowUpTimer * SPRITE_OFFSET,
					base_y, SPRITE_WIDTH, SPRITE_HEIGHT);
		}
		if (blowUpTimer == BLOWUP_FRAMES) {
			setVisible(false);
			blowUp();
		}
		if (hasBlownUp) {
			if (source != null)
				source.setBombs(source.getBombs() + 1);
			makeFire();
<<<<<<< HEAD
=======
			// Dont want the particles on the intro screen to make a horrible
			// noise
			if (source != null)
				SoundPlayer.play("sound11 bomb", 1.2f, 0.5f);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			destroy();
		}

	}

	public void blowUp() {

		if (!hasBlownUp) {
			hasBlownUp = true;
		}
		if (!ticking) {
			ticking = true;
		}
	}

	private void makeFire() {
		World.add(new Fire(x_index, y_index, source, power, Fire.ORIGIN,
				isSuper));
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z);
	}

	public boolean hasBlownUp() {
		return hasBlownUp;
	}

}
