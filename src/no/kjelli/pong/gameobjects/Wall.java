package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractObject;

public class Wall extends AbstractObject implements Collidable {

	public static final int DEFAULT_SIZE = 16;

	public Wall(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void update() {
	}

	@Override
	public void onCollide(Collision collision) {
	}

	@Override
	public String toString() {
		return "wall";
	}
}
