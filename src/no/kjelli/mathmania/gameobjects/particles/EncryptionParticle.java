package no.kjelli.mathmania.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.levels.Level;

public class EncryptionParticle extends AbstractParticle {
	private static final int base_x = 16, base_y = 80;
	public static final int SPRITE_SIZE = 16;
	public static final float SCALE = 1.0f;
	public static final float SIZE = SPRITE_SIZE * SCALE;

	public static final float angular_velocity = 0.041f;
	public static final float SPEED = 1.0f;
	public float angle;
	public static final long MAX_TIME_TO_LIVE = 150;
	public int animationframe = 0;

	public EncryptionParticle(float x, float y, float angle) {
		super(0, 0, SPRITE_SIZE, SPRITE_SIZE, MAX_TIME_TO_LIVE);
		this.angle = angle;
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
	}

	@Override
	public void updateParticle() {
		angle += angular_velocity;

		sprite.getColor().a = (float) timeToLive / (float) MAX_TIME_TO_LIVE;

		velocity_x = SPEED * Math.cos(angle);
		velocity_y = SPEED * Math.sin(angle);
		move();

		if (MathMania.ticks % 30 == 0) {
			if (animationframe == 0) {
				sprite.setTextureCoords(base_x + SPRITE_SIZE, base_y,
						SPRITE_SIZE, SPRITE_SIZE);
				animationframe = 1;
			} else {
				sprite.setTextureCoords(base_x, base_y, SPRITE_SIZE,
						SPRITE_SIZE);
				animationframe = 0;
			}
		}
	}

	@Override
	public void draw() {
		Draw.drawable(this, Level.getPlayer().getCenterX() - SIZE / 2, Level
				.getPlayer().getCenterY() - SIZE / 2);
	}

}
