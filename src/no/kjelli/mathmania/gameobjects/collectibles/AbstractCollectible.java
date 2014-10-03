package no.kjelli.mathmania.gameobjects.collectibles;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.mathmania.gameobjects.Player;
import no.kjelli.mathmania.gameobjects.Score;

public abstract class AbstractCollectible extends AbstractCollidable implements
		Collectible {

	public AbstractCollectible(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Player) {
			Score.addToScore(getScore());
			onCollect();
		}
	}

	protected abstract void onCollect();

}
