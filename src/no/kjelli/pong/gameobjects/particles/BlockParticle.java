package no.kjelli.pong.gameobjects.particles;

import org.newdawn.slick.Color;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.pong.Pong;

public class BlockParticle extends AbstractParticle {

	private static final int BASE_X = 0, BASE_Y = 224;
	public static final int SPRITE_SIZE = 8;

	private float angle;
	private float speed;

	private static final long MAX_TIME_TO_LIVE = 100;
	private static final float MAX_SPEED = 1.0f;
	private float INITIAL_SPEED;

	private static final float INITIAL_SCALE = 1.0f;

	public BlockParticle(float x, float y, float angle) {
		super(x, y, 3f, SPRITE_SIZE, SPRITE_SIZE, MAX_TIME_TO_LIVE);
		this.angle = angle % 360;
		INITIAL_SPEED = (float) (Math.random() * MAX_SPEED);
		speed = INITIAL_SPEED;
		sprite = new Sprite(TextureAtlas.objects, BASE_X, BASE_Y, SPRITE_SIZE,
				SPRITE_SIZE);

		float component = 1.0f;
		float r = (float) (component + (1 - component) * Math.random()), g = (float) (component + (1 - component)
				* Math.random()), b = (float) (component + (1 - component)
				* Math.random());
		sprite.setColor(new Color(r, g, b));

		timeToLive = MAX_TIME_TO_LIVE;
	}

	@Override
	public void updateParticle() {
		xScale = ((float) timeToLive / MAX_TIME_TO_LIVE) * INITIAL_SCALE;
		yScale = ((float) timeToLive / MAX_TIME_TO_LIVE) * INITIAL_SCALE;
		velocity_x = speed * Math.cos(angle);
		velocity_y = speed * Math.sin(angle);
		move();
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z, (Pong.ticks * INITIAL_SPEED * 10) % 360, width, height, false);
	}

}
