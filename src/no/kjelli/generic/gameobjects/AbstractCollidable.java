package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;

public abstract class AbstractCollidable extends AbstractGameObject implements
		Collidable {
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

		while (xStep >= 1) {
			xStep--;
			x++;
			Physics.getCollisions(this);
			if (colRight) {
				x--;
				xStep = 0;
				return;
			}
			microStep();

		}
		while (xStep <= -1) {
			xStep++;
			x--;
			Physics.getCollisions(this);
			if (colLeft) {
				x++;
				xStep = 0;
				return;

			}
			microStep();
		}
	}

	// To be overridden
	protected void microStep() {
	};

	public void yStep(double velocity_y) {
		yStep += velocity_y;

		while (yStep >= 1) {
			yStep--;
			y++;
			Physics.getCollisions(this);
			if (colAbove) {
				y--;
				yStep = 0;
				return;

			}
			microStep();
		}
		while (yStep <= -1) {
			yStep++;
			y--;
			Physics.getCollisions(this);
			if (colBelow) {
				y++;
				yStep = 0;
				return;
			}
			microStep();
		}
	}

	public abstract void onCollision(Collision collision);
}
