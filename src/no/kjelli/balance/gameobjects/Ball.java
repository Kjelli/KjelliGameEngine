package no.kjelli.balance.gameobjects;

import no.kjelli.balance.Balance;
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Textures;
import no.kjelli.generic.sound.SoundPlayer;

import org.newdawn.slick.opengl.Texture;

public class Ball extends AbstractCollidable {
	public static final int SIZE = 16;
	private static final float DAMPING = 0.15f;
	private float acceleration_x;

	private Texture texture_white;
	private Texture texture_yellow;
	private Texture texture_purple;

	public Ball(float x, float y) {
		this.x = x;
		this.y = y;
		width = SIZE;
		height = SIZE;
		texture_white = Textures.load("res\\ball.jpg");
		texture_yellow = Textures.load("res\\ball_yellow.jpg");
		texture_purple = Textures.load("res\\ball_purple.jpg");
		texture = texture_white;
	}

	@Override
	public void onCollision(Collision collision) {
		GameObject tgt = collision.getTarget();
		if (tgt instanceof Paddle) {
			stop(collision.getImpactDirection());
			if (collision.getImpactDirection() == Collision.BELOW)
				velocity_y *= -1.018;
			velocity_x = velocity_x * 0.5f + (getCenterX() - tgt.getCenterX())
					* DAMPING;
			acceleration_x = -(float) tgt.getVelocityX() * 0.005f;
			SoundPlayer.play("bounce");
		}
		if (tgt instanceof Wall) {
			stop(collision.getImpactDirection());
			velocity_x *= -1;
			acceleration_x *= -1;
			SoundPlayer.play("bounce");
		}
		
		if (tgt instanceof HoopEdge) {
			int direction = collision.getImpactDirection();
			stop(direction);
			if(direction == Collision.ABOVE || direction == Collision.BELOW)
				velocity_y *= -1;
			if(direction == Collision.LEFT || direction == Collision.RIGHT)
				velocity_x *= -1;
			SoundPlayer.play("bounce");
		}

	}

	@Override
	public void update() {
		velocity_y -= Balance.gravity;
		velocity_x += acceleration_x;
		move();
		
		if(y < 0){
			SoundPlayer.play("lose");
			destroy();
		}

	}
}
