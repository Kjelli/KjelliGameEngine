package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;

import org.newdawn.slick.Color;

public class HoopEdge extends AbstractCollidable {
	public static final int SIZE = 12;

	public HoopEdge(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;
		color = new Color(Draw.DEFAULT_COLOR);
		setTransparency(0f);
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub

	}

	public void setTransparency(float alpha) {
		color.a = alpha;
	}

	@Override
	public void update() {
	}

}
