package no.kjelli.bombline.gameobjects.powerups;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Sprite;

public class PowerupSpeed extends AbstractPowerup {
	public static final int BASE_X = 32, BASE_Y = 144, BASE_SIZE = 16;

	public PowerupSpeed(int x_index, int y_index) {
		super(x_index, y_index, 0.1f, SPEED);
		sprite = new Sprite(BombermanOnline.partybombs, BASE_X, BASE_Y, BASE_SIZE,
				BASE_SIZE);
	}

	@Override
	public void powerUp(Player player) {
		player.increaseSpeed();
	}

}
