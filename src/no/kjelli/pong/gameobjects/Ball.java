package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.AbstractObject;
import no.kjelli.generic.sound.SoundPlayer;

public class Ball extends AbstractCollidable {

	public static final int SIZE = 12;
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
		speed = 2f;
		
		loadTexture("res\\ball.jpg");
	}

	@Override
	public void update() {

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

		if (x + width < 0 || x > World.getWidth()) {
			destroy();
		}

		Collision collision = move();
		if (collision == null)
			return;

		Collidable target = collision.getTarget();
		if (target instanceof Bat || target instanceof Ball) {
			bounce(target);
		}
		if (target instanceof Bat)
			this.color = ((Bat) target).getColor();

		if (target instanceof Wall) {
			int direction = collision.getImpactDirection();

			if ((direction & (Collision.ABOVE | Collision.BELOW)) > 0) {
				velocity_y *= -1;
				angle = Math.atan2(velocity_y, velocity_x);
			}
			if ((direction & (Collision.LEFT | Collision.RIGHT)) > 0) {
				velocity_x *= -1;
				angle = Math.atan2(velocity_y, velocity_x);
			}
		}
		SoundPlayer.play("bounce",
				1 + (float) (Math.abs(velocity_x) / MAX_SPEED_X));
	}

	private void bounce(Collidable other) {
		float xlength = getCenterX() - other.getCenterX();
		float ylength = getCenterY() - other.getCenterY();

		angle = Math.atan2(ylength, xlength);
		speed += 0.4f;
	}

	@Override
	public String toString() {
		return "ball";
	}
}
