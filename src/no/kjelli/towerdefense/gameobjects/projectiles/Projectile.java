package no.kjelli.towerdefense.gameobjects.projectiles;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.gameobjects.towers.Tower;

public abstract class Projectile extends AbstractCollidable{

	public Projectile(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public void onCollision(Collision collision) {
		GameObject target = collision.getTarget();
		if(target instanceof Enemy)
			onCollide((Enemy)target);
		else if(target instanceof Tower)
			onCollide((Tower) target);
	}
	
	public abstract void onCollide(Enemy target);
	public abstract void onCollide(Tower target);

}
