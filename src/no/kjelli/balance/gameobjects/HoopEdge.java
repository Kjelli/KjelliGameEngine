package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;

public class HoopEdge extends AbstractCollidable{
	public static final int SIZE = 12;
	
	public HoopEdge(float x, float y){
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {}

}
