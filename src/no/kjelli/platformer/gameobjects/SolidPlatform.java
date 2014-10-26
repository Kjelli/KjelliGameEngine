package no.kjelli.platformer.gameobjects;

import no.kjelli.generic.Collision;

import org.newdawn.slick.Color;

public class SolidPlatform extends AbstractPlatform {

	public SolidPlatform(float x, float y, float width, float height) {
		super(x, y, width, height);
		color = new Color(0.25f, 0.5f, 0.75f);
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void update() {

	}

}
