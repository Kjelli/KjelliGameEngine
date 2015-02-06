package no.kjelli.pong.gameobjects.ai;

import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;

public interface AI {
	final float DEFAULT_MARGIN = Ball.HEIGHT*1.5f;

	void updateAI();

	String getName();

	int getPlayerNo();

	void setBat(Bat bat);

	int getDifficulty();
}
