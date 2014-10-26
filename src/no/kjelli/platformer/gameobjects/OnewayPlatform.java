package no.kjelli.platformer.gameobjects;

import no.kjelli.generic.Collision;

import org.newdawn.slick.Color;

public class OnewayPlatform extends AbstractPlatform {

	public OnewayPlatform(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void update() {

	}

	public float getTop() {
		return y + height;
	}

}
