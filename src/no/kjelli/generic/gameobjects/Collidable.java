package no.kjelli.generic.gameobjects;

import no.kjelli.generic.Collision;

public interface Collidable extends GameObject {

	void onCollision(Collision collision);
}
