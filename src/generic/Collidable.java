package generic;

import generic.gameobjects.GameObject;

public interface Collidable extends GameObject{
	public void onCollide(Collidable other);
}
