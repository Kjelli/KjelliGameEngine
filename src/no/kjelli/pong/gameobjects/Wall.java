package pong.gameobjects;

import generic.Collidable;
import generic.gameobjects.AbstractObject;

public class Wall extends AbstractObject implements Collidable {

	public static final int DEFAULT_SIZE = 16;

	private Ball ball;

	public Wall(float x, float y, float width, float height, Ball ball) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ball = ball;
	}

	@Override
	public void update() {
	}

	@Override
	public void onCollide(Collidable other) {
	}

	@Override
	public String toString() {
		return "wall";
	}
}
