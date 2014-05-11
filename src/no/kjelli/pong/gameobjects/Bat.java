package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Textures;

public class Bat extends AbstractCollidable {
	public static final int WIDTH = 16;
	public static final int HEIGHT = 128;
	public static final float SPEED = 3f;

	public Bat(float x, float y) {
		this.x = x;
		this.y = y;

		width = WIDTH;
		height = HEIGHT;

		texture = Textures.load("res\\bat.jpg");

	}

	@Override
	public void update() {
		move();
		velocity_y = 0;
	}

	public void move(double d) {
		velocity_y = SPEED * d;

	}

	@Override
	public String toString() {
		return "bat";
	}

	@Override
	public void onCollision(Collision collision) {
		Collidable target = collision.getTarget();
		if (target instanceof Wall)
			stop(collision.getImpactDirection());
	}
}
