package no.kjelli.partybombs.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.partybombs.Partybombs;

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

	private long ticks = 0;
	private int blowUpTimer = 0;
	private boolean hasBlownUp = false;
	private boolean newlyCreated = false;

	public Bomb(int x_index, int y_index, Player source, int power) {
		super(
				x_index * Partybombs.block_size + Partybombs.block_size / 2 - 6,
				y_index * Partybombs.block_size + Partybombs.block_size / 2 - 6,
				12, 12);
		this.x_index = x_index;
		this.y_index = y_index;
		this.source = source;
		this.power = power;
		sprite = new Sprite(TextureAtlas.partybombs, base_x, base_y,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		z = 2.0f;
		tag(Partybombs.tag_playfield);
	}

	@Override
	public void onCreate() {
		newlyCreated = true;
		Physics.getCollisions(this);
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
			makeFire();
			destroy();
		}

	}

	public void blowUp() {
		if (hasBlownUp)
			return;
		else {
			hasBlownUp = true;
		}
	}

	private void makeFire() {
		World.add(new Fire(x_index, y_index, source, power, Fire.ORIGIN));
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z);
	}

	public boolean hasBlownUp() {
		return hasBlownUp;
	}

}
