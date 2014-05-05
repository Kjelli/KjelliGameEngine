package no.kjelli.pong.gameobjects;

import org.newdawn.slick.Color;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;

public class EnemyBat extends Bat {

	private Ball target;

	public EnemyBat(float x, float y) {
		super(x, y);
		color = Color.red;
	}

	@Override
	public void update() {
		super.update();
		if (target == null)
			lookForBall();
		else if (target.isVisible())
			moveTowardsBall();
		else
			target = null;
	}

	private void moveTowardsBall() {
		float ydist = getCenterY() - target.getCenterY();
		if (ydist > getHeight() / 4) {
			move(-0.8f);
		} else if (ydist < -getHeight() / 4) {
			move(0.8f);
		}
	}

	private void lookForBall() {
		for (GameObject go : World.getObjects()) {
			if (go instanceof Ball) {
				target = (Ball) go;
				return;
			}
		}
	}
}
