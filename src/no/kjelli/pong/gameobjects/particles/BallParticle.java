package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.pong.gameobjects.Ball;

import org.newdawn.slick.Color;

public class BallParticle extends AbstractParticle {
	public static final float SPEED = 5f;
	public static final long TIME_TO_LIVE_MAX = 40;
	public Color color;
	public BallParticle(Ball ball) {
		super(ball.getX(), ball.getY(), ball.getZ(), ball.getWidth(), ball
				.getHeight(), TIME_TO_LIVE_MAX);
		this.color = new Color(ball.getColor());
	}

	@Override
	public void updateParticle() {
		width += 0.2 * SPEED;
		velocity_x = -0.1 * SPEED;
		height += 0.2 * SPEED;
		velocity_y = -0.1 * SPEED;
		color.a = (float) timeToLive / TIME_TO_LIVE_MAX;
		move();
	}

	@Override
	public void draw() {
		Draw.rect(this, color);
	}

}
