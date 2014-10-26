package no.kjelli.mathmania.gameobjects;

import static org.lwjgl.input.Keyboard.KEY_DOWN;
import static org.lwjgl.input.Keyboard.KEY_LEFT;
import static org.lwjgl.input.Keyboard.KEY_RIGHT;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.input.Keyboard.KEY_UP;
import static org.lwjgl.input.Keyboard.isKeyDown;
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.blocks.Block;
import no.kjelli.mathmania.levels.Level;

public class Player extends AbstractCollidable {

	private static final int base_x = 0, base_y = 64;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;

	private static final double ACCELERATION = 0.5f;
	private static final double MAX_SPEED = 3.0;

	public Block selection;

	public Player(float x, float y) {
		super(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_WIDTH,
				SPRITE_HEIGHT);
		tag(MathMania.tag_playfield);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		Collidable target = collision.getTarget();

		if (target instanceof Block) {
			stop(collision.getImpactDirection());
			if ((collision.getImpactDirection() & (Collision.LEFT | Collision.RIGHT)) > 0) {
				velocity_x *= 0;
			}
			if ((collision.getImpactDirection() & (Collision.ABOVE | Collision.BELOW)) > 0) {
				velocity_y *= 0;
			}
			if (((Block) target).isObstructionBlock())
				return;
			if (selection != null) {
				selection.select(false);
				selection = null;
			}
			selection = (Block) target;
			((Block) target).select(true);
		}

	}

	@Override
	public void update() {
		animate();
		handleInput();
		move();

		if (selection != null) {
			if (getDistance(selection) > 2 * Block.SPRITE_SIZE) {
				selection.select(false);
				selection = null;
			}
		}
	}

	private void animate() {
		sprite.setTextureCoords(base_x, base_y, SPRITE_WIDTH, SPRITE_HEIGHT);
	}

	private void handleInput() {
		handleMovementInput();
		handleActionInput();
	}

	private void handleActionInput() {
		if (selection != null && isKeyDown(KEY_SPACE))
			selection.question();
	}

	private void handleMovementInput() {
		if (isKeyDown(KEY_LEFT) && velocity_x > -MAX_SPEED) {
			velocity_x -= ACCELERATION;
		} else if (isKeyDown(KEY_RIGHT) && velocity_x < MAX_SPEED) {
			velocity_x += ACCELERATION;
		} else {
			if (velocity_x > ACCELERATION)
				velocity_x -= ACCELERATION;
			else if (velocity_x < -ACCELERATION)
				velocity_x += ACCELERATION;
			else
				velocity_x = 0;
		}

		if (isKeyDown(KEY_UP) && velocity_y < MAX_SPEED) {
			velocity_y += ACCELERATION;
		} else if (isKeyDown(KEY_DOWN) && velocity_y > -MAX_SPEED) {
			velocity_y -= ACCELERATION;
		} else {
			if (velocity_y > ACCELERATION)
				velocity_y -= ACCELERATION;
			else if (velocity_y < -ACCELERATION)
				velocity_y += ACCELERATION;
			else
				velocity_y = 0;
		}
	}

	@Override
	public void draw() {
		Draw.drawable(this, 0f, 0f, 0f, 0f, false);
	}

}
