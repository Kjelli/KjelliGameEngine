package no.kjelli.mathmania.gameobjects.particles;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.gameobjects.collectibles.Coin;

public class GlitterParticle extends AbstractParticle {

	private static final int BASE_X = 8, BASE_Y = 224;
	public static final int SPRITE_SIZE = 8;
	public static final float SCALE = 1f;
	public static final float SIZE = SPRITE_SIZE * SCALE;
	public static final int TIME_TO_LIVE_MAX = 200;

	public float rot = 0;
	public float rotVel;
	public int timeToLive;

	public GlitterParticle(float x, float y, Coin coin) {
		super(x, y, SIZE, SIZE);
		sprite = new Sprite(TextureAtlas.objects, BASE_X, BASE_Y, SPRITE_SIZE,
				SPRITE_SIZE);
		color = coin.getColor().brighter();
		timeToLive = TIME_TO_LIVE_MAX;
		rotVel = (float) Math.random() * 10 - 5;
	}

	@Override
	public void update() {
		y -= 0.1f;

		rot = (rot + rotVel) % 360;

		if (timeToLive > 0)
			timeToLive--;
		else
			destroy();
	}

	@Override
	public void draw() {
		Draw.sprite(this, 0, 0, rot, SCALE * (float) timeToLive
				/ (float) TIME_TO_LIVE_MAX, SCALE * (float) timeToLive
				/ (float) TIME_TO_LIVE_MAX, false);
	}

}
