package no.kjelli.generic;

import no.kjelli.generic.gameobjects.GameObject;

public interface Collidable extends GameObject {

	public void onCollide(Collision collision);

}
