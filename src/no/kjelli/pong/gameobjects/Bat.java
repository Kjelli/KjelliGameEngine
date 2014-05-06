package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.AbstractObject;

public class Bat extends AbstractCollidable {
	public static final int WIDTH = 16;
	public static final int HEIGHT = WIDTH * 7;
	public static final float SPEED = 3f;

	public Bat(float x, float y) {
		this.x = x;
		this.y = y;

		width = WIDTH;
		height = HEIGHT;

	}

	@Override
	public void update() {
		Collision collision = move();
		velocity_y = 0;
		if (collision == null)
			return;
		Collidable target = collision.getTarget();
		if (target instanceof Wall) {
			if (target.getCenterY() < this.getCenterY())
				velocity_y += SPEED * 1;
			else
				velocity_y -= SPEED * 1;
		}
	}

	public void move(double d) {
		velocity_y = SPEED * d;

	}

	@Override
	public String toString() {
		return "bat";
	}

}
