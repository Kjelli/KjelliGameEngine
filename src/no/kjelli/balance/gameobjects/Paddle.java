package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Textures;
import no.kjelli.generic.sound.SoundPlayer;

public class Paddle extends AbstractCollidable {
	public static final int WIDTH = 128, HEIGHT = 16;
	public static final int SINGLE_PLAYER = 0, PLAYER_ONE = 1, PLAYER_TWO = 2;
	public int playerID;

	public Paddle(float x, float y, int playerID) {
		this.x = x;
		this.y = y;
		this.playerID = playerID;
		width = WIDTH;
		height = HEIGHT;
		switch (playerID) {
		case 1:
			texture = Textures.load("res\\paddle_purple.jpg");
			break;
		case 2:
			texture = Textures.load("res\\paddle_yellow.jpg");
			break;
		default:
			texture = Textures.load("res\\paddle.jpg");
		}
	}

	@Override
	public void onCollision(Collision collision) {
		Collidable tgt = collision.getTarget();
		if (tgt instanceof Wall) {
			stop(collision.getImpactDirection());
		}
		if (tgt instanceof Paddle) {
			stop(collision.getImpactDirection());
			SoundPlayer.play("bounce");
		}
	}

	@Override
	public void update() {
		velocity_x *= 0.8f;
		move();
	}

	public void accelerate(int i) {
		velocity_x += i;
	}

}
