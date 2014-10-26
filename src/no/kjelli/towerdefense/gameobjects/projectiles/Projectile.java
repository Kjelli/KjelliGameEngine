package no.kjelli.towerdefense.gameobjects.projectiles;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.gameobjects.towers.Tower;

public abstract class Projectile extends AbstractCollidable {

	public Target target;
	protected int frames;

	public Projectile(float x, float y, float width, float height, float speed) {
		super(x, y, width, height);
		this.speed = speed;
	}

	@Override
	public void onCollision(Collision collision) {
		GameObject target = collision.getTarget();
		if (target instanceof Enemy)
			onCollide((Enemy) target);
		else if (target instanceof Tower)
			onCollide((Tower) target);
	}

	public void setTarget(Target target) {
		this.target = target;
		double angle = Math.atan2(target.getCenterY() - getCenterY(),
				target.getCenterX() - getCenterX());
		velocity_x = Math.cos(angle) * speed;
		velocity_y = Math.sin(angle) * speed;
	}

	@Override
	public void update() {
		frames++;
	}

	public abstract void onCollide(Enemy target);

	public abstract void onCollide(Tower target);

}
