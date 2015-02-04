package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.tweens.GameObjectAccessor;
import no.kjelli.pong.gameobjects.Ball;

import org.newdawn.slick.Color;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Expo;
import aurelienribon.tweenengine.equations.Quad;

public class BallParticleReverse extends AbstractParticle {
	public static final float SPEED = 5f;
	public static final long TIME_TO_LIVE_MAX = 80;
	public Color color;

	public BallParticleReverse(Ball ball) {
		super(ball.getX() - (SPEED * 0.1f * TIME_TO_LIVE_MAX), ball.getY()
				- (SPEED * 0.1f * TIME_TO_LIVE_MAX), ball.getZ(), ball
				.getWidth() + SPEED * 0.2f * TIME_TO_LIVE_MAX, ball.getHeight()
				+ SPEED * 0.2f * TIME_TO_LIVE_MAX, TIME_TO_LIVE_MAX);
		this.color = new Color(ball.getColor());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		int dir = (int) Math.floor(Math.random() * 2) * 2 - 1;
		Tween.to(this, GameObjectAccessor.ROTATION, 1000).target(dir*360)
				.ease(aurelienribon.tweenengine.equations.Circ.OUT)
				.start(GameWrapper.tweenManager);
	}

	@Override
	public void updateParticle() {
		width -= 0.2 * SPEED;
		velocity_x = 0.1 * SPEED;
		height -= 0.2 * SPEED;
		velocity_y = 0.1 * SPEED;
		color.a = 1.0f - (float) timeToLive / TIME_TO_LIVE_MAX;
		move();
	}

	@Override
	public void draw() {
		Draw.rect(this, color);
	}

}
