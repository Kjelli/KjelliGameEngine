package pong.gameobjects;

import generic.Collidable;
import generic.Physics;
import generic.gameobjects.AbstractObject;

import org.lwjgl.opengl.Display;

public class Ball extends AbstractObject implements Collidable {

	public static final int SIZE = 16;
	public static final float MIN_SPEED_X = 2f;
	public static final float MAX_SPEED_X = 3f;
	public static final float MAX_SPEED_Y = 8f;
	public static final float DAMPING = 0.05f;

	public static double angle = Math.PI;
	public static float speed = 1.0f;

	public Ball(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;

		velocity_x = -MIN_SPEED_X;
		velocity_y = 0;
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
			x = Display.getWidth() / 2;
			y = Display.getHeight() / 2 - height / 2;
			velocity_x = -MAX_SPEED_X;
			velocity_y = 0;
			speed = 1;
			angle = Math.PI;
		}

		x += velocity_x;
		y += velocity_y;
	}

	@Override
	public void onCollide(Collidable other) {

		if (other instanceof Bat) {
			float xlength = getCenterX() - other.getCenterX();
			float ylength = getCenterY() - other.getCenterY();

			angle = Math.atan2(ylength, xlength);

			speed += 0.04;

		}
		if (other instanceof Wall) {
			angle = 2 * Math.PI - angle;
		}
	}
}
