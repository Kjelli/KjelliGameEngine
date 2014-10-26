package no.kjelli.platformer.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.platformer.Platformer;

import org.newdawn.slick.Color;

public class MovingOnewayPlatform extends OnewayPlatform {
	private final float max_distance = 100;
	public float startingX;
	public float startingY;

	public MovingOnewayPlatform(float x, float y, float width, float height) {
		super(x, y, width, height);
		startingX = x;
		startingY = y;
		z = 1.1f;
		color = new Color(0.25f, 0.5f, 0.75f);
		velocity_x = 1.0f;
		velocity_y = 0.0f; // TODO
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void update() {
		if (Math.abs(x - startingX) > max_distance)
			velocity_x *= -1;

		move();
	}

}
