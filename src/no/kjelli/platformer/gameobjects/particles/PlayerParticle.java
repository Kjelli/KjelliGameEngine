package no.kjelli.platformer.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.platformer.gameobjects.Player;

public class PlayerParticle extends AbstractParticle {
	public static final long MAX_TIME_TO_LIVE = 5;

	public PlayerParticle(Player player) {
		super(player.getX(), player.getY(), player.getWidth(), player
				.getHeight(), MAX_TIME_TO_LIVE);
		sprite = new Sprite(player.getSprite());
		z = 0.9f;
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z);
	}

	@Override
	public void updateParticle() {
		sprite.getColor().a = (float) timeToLive / MAX_TIME_TO_LIVE;
		z -= 0.01f;
	}

}
