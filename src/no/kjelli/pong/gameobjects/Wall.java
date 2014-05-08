package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collidable;
import no.kjelli.generic.gameobjects.AbstractObject;

public class Wall extends AbstractObject implements Collidable {

	public static final int DEFAULT_SIZE = 16;

	public Wall(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		loadTexture("res\\ball.jpg");
	}

	@Override
	public void update() {
	}

	@Override
	public String toString() {
		return "wall";
	}
}
