package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Screen;

public class SpaceShip extends AbstractCollidable {

	public static final int WIDTH = 64, HEIGHT = 8;
	public static final float SPEED = 3;

	public SpaceShip(float x, float y) {
		this.x = x;
		this.y = y;
		width = WIDTH;
		height = HEIGHT;
		if (x > Screen.getWidth() / 2)
			velocity_x = -SPEED;
		else
			velocity_x = SPEED;
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Ball )
			destroy();
	}

	@Override
	public void update() {
		move();
		if (x > Screen.getWidth())
			x = -WIDTH;
		else if (x < 0)
			x = Screen.getWidth();
	}

}
