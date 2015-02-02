package no.kjelli.tweentest;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;

public class TweenObject extends AbstractCollidable{

	public TweenObject(float x, float y) {
		super(x, y, 2, 0,0);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
		Draw.fillRect(x,y,width,height, rotation);
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub
		
	}

}
