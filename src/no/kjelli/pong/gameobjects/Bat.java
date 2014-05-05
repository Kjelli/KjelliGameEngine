package pong.gameobjects;

import generic.Collidable;
import generic.Physics;
import generic.gameobjects.AbstractObject;

public class Bat extends AbstractObject implements Collidable {
	public static final int WIDTH = 16;
	public static final int HEIGHT = WIDTH * 7;
	public static final float SPEED = 4f;

	public Bat(float x, float y) {
		this.x = x;
		this.y = y;

		width = WIDTH;
		height = HEIGHT;

	}

	@Override
	public void update() {
		Physics.checkCollision(this);
		y += velocity_y;
		velocity_y = 0;

	}

	public void move(float mag) {
		velocity_y = SPEED * mag;
	}

	@Override
	public void onCollide(Collidable other) {
		if (other instanceof Wall) {
			if (other.getCenterY() < this.getCenterY())
				velocity_y += SPEED * 1;
			else
				velocity_y -= SPEED * 1;
		}
	}

	@Override
	public String toString() {
		return "bat";
	}

}
