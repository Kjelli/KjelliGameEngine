package no.kjelli.pong.gameobjects.ai;

import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;

public abstract class AbstractAI implements AI {

	protected Bat bat;
	protected int reaction;
	protected float margin;

	public AbstractAI(int reactionFactor, float margin) {
		this.reaction = reactionFactor;
		this.margin = margin;
	}

	protected void followBall() {
		if (Pong.ball.getCenterY() > bat.getCenterY() + DEFAULT_MARGIN * margin
				* (bat.getHeight() / Bat.HEIGHT)) {
			bat.setVelocityY(Math.min(
					Math.max(0.0f, (Pong.ball.getCenterY() - bat.getCenterY())
							/ (5 * reaction)), Bat.SPEED_MAX));
		} else if (Pong.ball.getCenterY() < bat.getCenterY() - DEFAULT_MARGIN
				* margin * (bat.getHeight() / Bat.HEIGHT)) {
			bat.setVelocityY(Math.max(Math.min(-0.0f,
					-(bat.getCenterY() - Pong.ball.getCenterY())
							/ (5 * reaction)), -Bat.SPEED_MAX));
		} else {
			bat.deccelerate();
		}
	}

	protected void stop() {
		bat.deccelerate();
	}

	@Override
	public abstract void updateAI();

	@Override
	public abstract String getName();

	@Override
	public int getPlayerNo() {
		return 1;
	}

	@Override
	public void setBat(Bat bat) {
		this.bat = bat;
	}

}
