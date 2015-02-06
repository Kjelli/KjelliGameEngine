package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.pong.gameobjects.Ball;

import org.newdawn.slick.Color;

public class BallHitParticle extends AbstractParticle {
	public static final float SPEED = 5f;
	public final long TIME_TO_LIVE_MAX;
	public Color color;

	public BallHitParticle(Ball ball) {
		super(ball.getX(), ball.getY(), ball.getZ(), ball.getWidth(), ball
				.getHeight(), (long) (ball.speed * 10));
		TIME_TO_LIVE_MAX = (long) (ball.speed * 10);
		this.color = new Color(ball.getColor());
	}

	@Override
	public void updateParticle() {
		width += TIME_TO_LIVE_MAX/100.0f * SPEED;
		velocity_x = -TIME_TO_LIVE_MAX/200.0f * SPEED;
		height += TIME_TO_LIVE_MAX/100.0f * SPEED;
		velocity_y = -TIME_TO_LIVE_MAX/200.0f * SPEED;
		color.a = (float) timeToLive / TIME_TO_LIVE_MAX;
		move();
	}

	@Override
	public void draw() {
		Draw.rect(this, color);
	}

}
