package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.Physics;
import no.kjelli.generic.gameobjects.AbstractObject;

import org.lwjgl.opengl.Display;

public class Ball extends AbstractObject implements Collidable {

	public static final int SIZE = 16;
	public static final float MIN_SPEED_X = 1f;
	public static final float MAX_SPEED_X = 5f;
	public static final float MAX_SPEED_Y = 8f;
	public static final float DAMPING = 0.05f;

	public double angle = Math.PI;

	public Ball(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;

		angle = -Math.PI;
		speed = 2.0f;
	}

	@Override
	public void update() {
		Physics.checkCollision(this);

		velocity_x = Math.cos(angle) * speed;
		velocity_y = Math.sin(angle) * speed;

		if (velocity_x < MIN_SPEED_X && velocity_x > 0)
			velocity_x = MIN_SPEED_X;
		if (velocity_x > -MIN_SPEED_X && velocity_x < 0)
			velocity_x = -MIN_SPEED_X;

		if (velocity_x > MAX_SPEED_X)
			velocity_x = MAX_SPEED_X;
		if (velocity_x < -MAX_SPEED_X)
			velocity_x = -MAX_SPEED_X;

		if (velocity_y > MAX_SPEED_Y)
			velocity_y = MAX_SPEED_Y;
		if (velocity_y < -MAX_SPEED_Y)
			velocity_y = -MAX_SPEED_Y;

		if (x + width < 0 || x > Display.getWidth()) {
			destroy();
		}

		x += velocity_x;
		y += velocity_y;
	}

	@Override
	public void onCollide(Collidable other) {
		if (other instanceof Bat || other instanceof Ball) {
			float xlength = getCenterX() - other.getCenterX();
			float ylength = getCenterY() - other.getCenterY();

			angle = Math.atan2(ylength, xlength);

			x += velocity_x;
			y += velocity_y;
			speed += 0.04f;

		}
		if (other instanceof Wall) {
			angle = 2 * Math.PI - angle;
		}
	}

	@Override
	public String toString() {
		return "ball";
	}
}
