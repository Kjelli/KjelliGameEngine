package no.kjelli.bombline.gameobjects.powerups;

import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.generic.gameobjects.GameObject;

public interface Powerup extends GameObject {
	public static final int BOMB = 0, FIRE = 1, SPEED = 2;
	static final int BACKGROUND_BASE_X = 0;
	static final int BACKGROUND_BASE_Y = 128;
	static final long ANIMATION_TIMER = 20;
	static final int ANIMATION_FRAMES = 2;
	public static final int SPRITE_WIDTH = 16, SPRITE_HEIGHT = 16;
	public static final int DRAW_X_OFFSET = -2, DRAW_Y_OFFSET = -2;

<<<<<<< HEAD
	void powerUp(Player player);
=======
	void collect(Player player);
	void powerUpEffect(Player player);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	void blowUp();

	int getType();
}
