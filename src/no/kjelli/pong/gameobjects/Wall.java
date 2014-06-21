package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Textures;

public class Wall extends AbstractGameObject implements Collidable {

	public static final int DEFAULT_SIZE = 16;

	public Wall(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		texture = Textures.load("res\\ball.jpg");
	}

	@Override
	public void update() {
	}

	@Override
	public String toString() {
		return "wall";
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void draw() {
		Draw.texture(this);
	}
}
