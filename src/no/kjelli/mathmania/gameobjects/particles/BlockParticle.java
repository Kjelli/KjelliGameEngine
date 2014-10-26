package no.kjelli.mathmania.gameobjects.particles;

import org.newdawn.slick.Color;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Combo;

public class BlockParticle extends AbstractParticle {

	private static final int BASE_X = 8, BASE_Y = 224;
	public static final int SPRITE_SIZE = 8;

	private float angle;
	private float speed;

	private static final long MAX_TIME_TO_LIVE = 100;
	private static final float MAX_SPEED = 3.0f;
	private float INITIAL_SPEED;

	private static final float INITIAL_SCALE = 1.0f;

	public BlockParticle(float x, float y, float angle) {
		super(x, y, SPRITE_SIZE, SPRITE_SIZE, MAX_TIME_TO_LIVE);
		this.angle = angle % 360;
		INITIAL_SPEED = (float) (Math.random() * MAX_SPEED);
		speed = INITIAL_SPEED;
		sprite = new Sprite(TextureAtlas.objects, BASE_X, BASE_Y, SPRITE_SIZE,
				SPRITE_SIZE);

		float component = Combo.getPercentage();
		float r = (float) (component + (1 - component) * Math.random()), g = (float) (component + (1 - component)
				* Math.random()), b = (float) (component + (1 - component)
				* Math.random());
		sprite.setColor(new Color(r, g, b));

		timeToLive = (long) ((Math.random() * Math.min(
				(float) Combo.getCount() / 5, 3)) * MAX_TIME_TO_LIVE);
	}

	@Override
	public void updateParticle() {
		xScale = ((float) timeToLive / MAX_TIME_TO_LIVE) * INITIAL_SCALE;
		yScale = ((float) timeToLive / MAX_TIME_TO_LIVE) * INITIAL_SCALE;
		angle += Math.random() * 0.5f - 0.25f;
		velocity_x = speed * Math.cos(angle);
		velocity_y = speed * Math.sin(angle);
		speed = ((float) timeToLive / MAX_TIME_TO_LIVE) * INITIAL_SPEED;
		move();
	}

	@Override
	public void draw() {
		Draw.drawable(this, 0f, 0f, 0f,
				(MathMania.ticks * INITIAL_SPEED * 10) % 360, false);
	}

}
