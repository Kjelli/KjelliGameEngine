package no.kjelli.pong.gameobjects.ai;

import no.kjelli.generic.gfx.Screen;
import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;

public class EasyAI extends AbstractAI {

	public EasyAI() {
		super(8, 1);
	}

	@Override
	public void updateAI() {
		if (Pong.ball.getX() > Screen.getCenterX()) {
			followBall();
		} else {
			stop();
		}
		bat.move();
	}

	@Override
	public String getName() {
		return "EasyBot";
	}

	@Override
	public int getDifficulty() {
		return 1;
	}

}
