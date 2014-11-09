package no.kjelli.onlinetest.gameobjects.particles;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.onlinetest.OnlineTest;
import no.kjelli.onlinetest.gameobjects.Bomb;

public class BombParticle extends AbstractParticle {
	Bomb bomb;
	public BombParticle(float x, float y, int power, boolean ticking) {
		super(x, y, Bomb.SPRITE_WIDTH, Bomb.SPRITE_HEIGHT, 1);
		bomb = new Bomb((int) (x / OnlineTest.block_size),
				(int) (y / OnlineTest.block_size), null, power, ticking);
		World.add(bomb);
		sustain(true);
		bomb.getSprite().getColor().a = 0;

	}

	@Override
	public void draw() {
		Draw.sprite(bomb.getSprite(), x, y, z, 0, xScale, yScale, false, false, false);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void updateParticle() {
		if (bomb.getSprite().getColor().a < 1f)
			bomb.getSprite().getColor().a += 0.1f;
		bomb.update();
		if (bomb.hasBlownUp()) {
			destroy();
		}
	}

}
