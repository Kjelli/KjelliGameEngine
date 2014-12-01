package no.kjelli.generic.gfx;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class AbstractParticle extends AbstractGameObject {
	protected long timeToLive;
	private boolean sustain = false;

	public AbstractParticle(float x, float y, float z, float width,
			float height, long timeToLive) {
		super(x, y, z, width, height);
		if (timeToLive > 0)
			this.timeToLive = timeToLive;
		else
			throw new IllegalArgumentException("Illegal TTL: " + timeToLive);
	}

	@Deprecated
	public AbstractParticle(float x, float y, float width, float height,
			long timeToLive) {
		super(x, y, width, height);
		if (timeToLive > 0)
			this.timeToLive = timeToLive;
		else
			throw new IllegalArgumentException("Illegal TTL: " + timeToLive);
	}

	protected void sustain(boolean sustain) {
		this.sustain = sustain;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
		updateParticle();
		if (timeToLive > 0 && !sustain) {
			timeToLive--;
		} else if (!sustain)
			destroy();
	}

	public abstract void updateParticle();

	@Override
	public abstract void draw();

}
