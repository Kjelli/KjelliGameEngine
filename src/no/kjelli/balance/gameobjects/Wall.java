package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Textures;

public class Wall extends AbstractCollidable {
	public static final int SIZE = 8;

	public Wall(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		texture = Textures.load("res\\ball.jpg");
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void update() {
	}

}
