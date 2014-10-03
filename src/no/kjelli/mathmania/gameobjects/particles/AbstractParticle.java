package no.kjelli.mathmania.gameobjects.particles;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;

public abstract class AbstractParticle extends AbstractGameObject {

	public AbstractParticle(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public abstract void update();

	@Override
	public abstract void draw();

}
