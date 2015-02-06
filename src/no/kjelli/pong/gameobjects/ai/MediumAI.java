package no.kjelli.pong.gameobjects.ai;

import no.kjelli.generic.gfx.Screen;
import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;

public class MediumAI extends AbstractAI {

	public MediumAI() {
		super(2, 0.5f);
	}

	@Override
	public void updateAI() {

		if (Math.random() < 0.01f)
			bat.shoot();

		if (Pong.ball.getX() > Screen.getWidth() * 0.35) {
			followBall();
		} else {
			stop();
		}
		bat.move();
	}

	@Override
	public String getName() {
		return "MediumBot";
	}

	@Override
	public int getDifficulty() {
		return 2;
	}

}
