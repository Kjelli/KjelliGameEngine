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

	}

	public void move(float mag) {
		velocity_y = SPEED * mag;
	}

	@Override
	public void onCollide(Collidable other) {
		y -= velocity_y;
	}
}
