package no.kjelli.platformer.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import org.newdawn.slick.Color;

public abstract class AbstractPlatform extends AbstractCollidable {

	protected Color color = new Color(Color.white);

	public AbstractPlatform(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, z, width, height, 0, color);
	}

}
