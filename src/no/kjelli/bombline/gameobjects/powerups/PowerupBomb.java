package no.kjelli.bombline.gameobjects.powerups;

<<<<<<< HEAD
import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.gfx.Sprite;
=======
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

public class PowerupBomb extends AbstractPowerup {
	public static final int BASE_X = 0, BASE_Y = 144, BASE_SIZE = 16;

	public PowerupBomb(int x_index, int y_index) {
		super(x_index, y_index, 0.1f, BOMB);
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
=======
	public void powerUpEffect(Player player) {
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		player.setBombs(player.getBombs() + 1);
		player.setBombCapacity(player.getBombCapacity() + 1);
	}

}
