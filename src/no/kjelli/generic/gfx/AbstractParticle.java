package no.kjelli.generic.gfx;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class AbstractParticle extends AbstractGameObject {
	protected long timeToLive;

	public AbstractParticle(float x, float y, float z, float width,
			float height, long timeToLive) {
		super(x, y, z, width, height);
		this.timeToLive = timeToLive;
	}

	@Deprecated
	public AbstractParticle(float x, float y, float width, float height,
			long timeToLive) {
		this(x, y, 0, width, height, timeToLive);

	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		updateParticle();
		if (timeToLive > 0) {
			timeToLive--;
		} else if (timeToLive == 0)
			destroy();
	}

	public abstract void updateParticle();

	@Override
	public abstract void draw();

}
