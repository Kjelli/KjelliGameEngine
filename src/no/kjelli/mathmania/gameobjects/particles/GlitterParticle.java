package no.kjelli.mathmania.gameobjects.particles;

import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class GlitterParticle extends AbstractParticle {

	private static final int BASE_X = 8, BASE_Y = 224;
	public static final int SPRITE_SIZE = 8;
	public static final float SCALE = 1f;
	public static final float SIZE = SPRITE_SIZE * SCALE;
	public static final int TIME_TO_LIVE_MAX = 200;

	public float rot = 0;
	public float rotVel;
	public int timeToLive;

	public GlitterParticle(float x, float y, GameObject object) {
		super(x, y, SIZE, SIZE, TIME_TO_LIVE_MAX);
		sprite = new Sprite(TextureAtlas.objects, BASE_X, BASE_Y, SPRITE_SIZE,
				SPRITE_SIZE);
		sprite.setColor(object.getSprite().getColor().brighter());
		rotVel = (float) Math.random() * 10 - 5;

		velocity_y = -0.1f;
	}

	public GlitterParticle(float x, float y, float z, GameObject object) {
		super(x, y, z, SIZE, SIZE, TIME_TO_LIVE_MAX);
		sprite = new Sprite(TextureAtlas.objects, BASE_X, BASE_Y, SPRITE_SIZE,
				SPRITE_SIZE);
		sprite.setColor(object.getSprite().getColor().brighter());
		rotVel = (float) Math.random() * 10 - 5;

		velocity_y = -0.1f;
	}

	@Override
	public void updateParticle() {
		rot = (rot + rotVel) % 360;
		xScale = (float) timeToLive / (float) TIME_TO_LIVE_MAX;
		yScale = (float) timeToLive / (float) TIME_TO_LIVE_MAX;
		move();
	}

	@Override
	public void draw() {
		Draw.drawable(this, 0, 0, 0, rot, false);
	}
}
