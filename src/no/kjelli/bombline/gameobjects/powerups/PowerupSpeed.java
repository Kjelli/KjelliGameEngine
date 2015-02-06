package no.kjelli.bombline.gameobjects.powerups;

<<<<<<< HEAD
import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Sprite;
=======
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.gameobjects.particles.GlitterParticle;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

public class PowerupSpeed extends AbstractPowerup {
	public static final int BASE_X = 32, BASE_Y = 144, BASE_SIZE = 16;

	public PowerupSpeed(int x_index, int y_index) {
		super(x_index, y_index, 0.1f, SPEED);
<<<<<<< HEAD
		sprite = new Sprite(BombermanOnline.partybombs, BASE_X, BASE_Y, BASE_SIZE,
=======
		sprite = new Sprite(TextureAtlas.partybombs, BASE_X, BASE_Y, BASE_SIZE,
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
				BASE_SIZE);
	}

	@Override
<<<<<<< HEAD
	public void powerUp(Player player) {
		player.increaseSpeed();
=======
	public void powerUpEffect(Player player) {
		player.increaseSpeed();
		World.add(new GlitterParticle(player.getX(), player.getY(), player
				.getZ() + 0.1f, player));
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

}
