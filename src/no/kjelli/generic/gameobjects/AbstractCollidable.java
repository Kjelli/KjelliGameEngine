package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.Collision;
import no.kjelli.generic.Physics;

public abstract class AbstractCollidable extends AbstractObject implements
		Collidable {
	protected float xStep;
	protected float yStep;

	public void validate() {
		Physics.revalidate(this);
	}

	protected Collision move() {

		xStep += velocity_x;

		while (xStep >= 1) {
			xStep--;
			x++;
			Collision check = Physics.checkCollision((Collidable) this);
			if (check != null) {
				if ((check.getImpactDirection() & Collision.RIGHT) > 0)
					x--;
				xStep = 0;
				return check;
			}

		}
		while (xStep <= -1) {
			xStep++;
			x--;
			Collision check = Physics.checkCollision((Collidable) this);
			if (check != null) {
				if ((check.getImpactDirection() & Collision.LEFT) > 0)
					x++;
				xStep = 0;
				return check;

			}
		}

		yStep += velocity_y;

		while (yStep >= 1) {
			yStep--;
			y++;
			Collision check = Physics.checkCollision((Collidable) this);
			if (check != null) {
				if ((check.getImpactDirection() & Collision.ABOVE) > 0)
					y--;
				yStep = 0;
				return check;

			}
		}
		while (yStep <= -1) {
			yStep++;
			y--;
			Collision check = Physics.checkCollision((Collidable) this);
			if (check != null) {
				if ((check.getImpactDirection() & Collision.BELOW) > 0)
					y++;
				yStep = 0;
				return check;

			}
		}
		validate();

		return null;
	}
}
