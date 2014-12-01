package no.kjelli.bombline.gameobjects.powerups;

import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class PowerupBomb extends AbstractPowerup {
	public static final int BASE_X = 0, BASE_Y = 144, BASE_SIZE = 16;

	public PowerupBomb(int x_index, int y_index) {
		super(x_index, y_index, 0.1f, BOMB);
		sprite = new Sprite(TextureAtlas.partybombs, BASE_X, BASE_Y, BASE_SIZE,
				BASE_SIZE);
	}

	@Override
	public void powerUp(Player player) {
		player.setBombs(player.getBombs() + 1);
		player.setBombCapacity(player.getBombCapacity() + 1);
	}

}
