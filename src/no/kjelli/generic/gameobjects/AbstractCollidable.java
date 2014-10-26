package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;

public abstract class AbstractCollidable extends AbstractGameObject implements
		Collidable {
	public AbstractCollidable(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	private float xStep;
	private float yStep;

	private boolean colLeft;
	private boolean colAbove;
	private boolean colRight;
	private boolean colBelow;

	protected void stop(int impactDirection) {
		colLeft = ((impactDirection & Collision.LEFT) > 0  || (colLeft));
		colAbove = ((impactDirection & Collision.ABOVE) > 0 || (colAbove));
		colRight = ((impactDirection & Collision.RIGHT) > 0 || (colRight));
		colBelow = ((impactDirection & Collision.BELOW) > 0 || (colBelow));

	}

	@Override
	public void move() {
		colLeft = false;
		colRight = false;
		xStep();
		colAbove = false;
		colBelow = false;
		yStep();
	}

	private void xStep() {
		xStep += velocity_x;

		while (xStep >= 1) {
			xStep--;
			x++;
			Physics.getCollisions((Collidable) this);
			if (colRight) {
				x--;
				xStep = 0;
				return;
			}

		}
		while (xStep <= -1) {
			xStep++;
			x--;
			Physics.getCollisions((Collidable) this);
			if (colLeft) {
				x++;
				xStep = 0;
				return;

			}
		}
	}

	public void yStep() {
		yStep += velocity_y;

		while (yStep >= 1) {
			yStep--;
			y++;
			Physics.getCollisions((Collidable) this);
			if (colAbove) {
				y--;
				yStep = 0;
				return;

			}
		}
		while (yStep <= -1) {
			yStep++;
			y--;
			Physics.getCollisions((Collidable) this);
			if (colBelow) {
				y++;
				yStep = 0;
				return;
			}
		}
	}

	public abstract void onCollision(Collision collision);
}
