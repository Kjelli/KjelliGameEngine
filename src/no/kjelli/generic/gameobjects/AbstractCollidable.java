package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;

public abstract class AbstractCollidable extends AbstractGameObject implements
		Collidable {
	private static final float stepSize = 1f;

	@Deprecated
	public AbstractCollidable(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public AbstractCollidable(float x, float y, float z, float width,
			float height) {
		super(x, y, z, width, height);
	}

	private float xStep;
	private float yStep;

	private boolean colLeft;
	private boolean colAbove;
	private boolean colRight;
	private boolean colBelow;

	protected void stop(int impactDirection) {
		colLeft = ((impactDirection & Collision.LEFT) > 0 || (colLeft));
		colAbove = ((impactDirection & Collision.UP) > 0 || (colAbove));
		colRight = ((impactDirection & Collision.RIGHT) > 0 || (colRight));
		colBelow = ((impactDirection & Collision.BELOW) > 0 || (colBelow));

	}

	@Override
	public void move() {
		move(velocity_x, velocity_y);
	}

	public void move(double velocity_x, double velocity_y) {
		colAbove = false;
		colBelow = false;
		yStep(velocity_y);
		colLeft = false;
		colRight = false;
		xStep(velocity_x);
	}

	private void xStep(double velocity_x) {
		xStep += velocity_x;

		while (xStep >= stepSize) {
			xStep -= stepSize;
			x += stepSize;
			Physics.getCollisions(this);
			microStep(xStep, 0);
			if (colRight) {
				x -= stepSize;
				xStep = 0;
				return;
			}

		}
		while (xStep <= -stepSize) {
			xStep += stepSize;
			x -= stepSize;
			Physics.getCollisions(this);
			microStep(xStep, 0);
			if (colLeft) {
				x += stepSize;
				xStep = 0;
				return;

			}
		}
	}

	protected void microStep(float xStep, float yStep) {
		// To be optionally overwritten
		
	}

	public void yStep(double velocity_y) {
		yStep += velocity_y;

		while (yStep >= stepSize) {
			yStep -= stepSize;
			y += stepSize;
			Physics.getCollisions(this);
			microStep(0, yStep);
			if (colAbove) {
				y -= stepSize;
				yStep = 0;
				return;

			}
		}
		while (yStep <= -stepSize) {
			yStep += stepSize;
			y -= stepSize;
			Physics.getCollisions(this);
			microStep(0, yStep);
			if (colBelow) {
				y += stepSize;
				yStep = 0;
				return;
			}
		}
	}

	public abstract void onCollision(Collision collision);
}
