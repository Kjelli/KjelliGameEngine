package no.kjelli.generic.gfx;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class AbstractParticle extends AbstractGameObject {
	protected long timeToLive;

	public AbstractParticle(float x, float y, float width, float height,
			long timeToLive) {
		super(x, y, width, height);
		if (timeToLive > 0)
			this.timeToLive = timeToLive;
		else
			throw new IllegalArgumentException("Illegal TTL: " + timeToLive);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		if (timeToLive > 0) {
			timeToLive--;
			updateParticle();
		} else
			destroy();
	}

	public abstract void updateParticle();

	@Override
	public abstract void draw();

}
