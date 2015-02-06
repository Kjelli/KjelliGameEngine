package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.pong.gameobjects.Ball;

import org.newdawn.slick.Color;

public class BallParticle extends AbstractParticle {
	public static final float SPEED = 5f;
	public static final long TIME_TO_LIVE_MAX = 40;
	public Color color;
	public Color whiteColor = new Color(Color.white);
	public BallParticle(Ball ball, float xOffset, float yOffset) {
		super(xOffset, yOffset, ball.getZ(), ball.getWidth(), ball
				.getHeight(), TIME_TO_LIVE_MAX);
		this.color = new Color(ball.getColor());
	}

	@Override
	public void updateParticle() {
		whiteColor.a = color.a = (float) timeToLive / (2*TIME_TO_LIVE_MAX);
		z+=0.001f;
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, 2f, width, height, color);
	}

}
