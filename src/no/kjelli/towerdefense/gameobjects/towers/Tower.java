package no.kjelli.towerdefense.gameobjects.towers;

import java.util.HashSet;
import java.util.LinkedHashSet;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.gameobjects.projectiles.Target;
import no.kjelli.towerdefense.map.Tile;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public abstract class Tower extends AbstractCollidable implements Target {

	protected Tile tile;
	int x_index, y_index;

	public boolean selected;

	public boolean targetsEnemies;
	
	Sprite idle_sprite;
	Sprite busy_sprite;

	int fire_cooldown_max;
	int fire_cooldown;
	float tile_range;

	public Tower(Tile tile, float tile_range, boolean targetsEnemies,
			int fire_cooldown_max) {
		super(0, 0, Tile.SIZE, Tile.SIZE);
		this.tile = tile;
		this.tile.tower = this;
		this.x_index = tile.x_index;
		this.y_index = tile.y_index;
		x = tile.getX();
		y = tile.getY();

		this.fire_cooldown_max = fire_cooldown_max;
		this.tile_range = tile_range;
	}

	public abstract void shoot(Target t);

	public abstract void afterShoot();

	public void onSelect() {
		// TODO:
	}

	public void onUnselect() {
		// TODO

	}

	@Override
	public void update() {
		
		if (fire_cooldown > 0){
			fire_cooldown--;
			if(fire_cooldown == 0)
				sprite = idle_sprite;
		}
		else {
			HashSet<Target> targets = getTargetsInRange();
			if (!targets.isEmpty()) {
				shoot(targets.iterator().next());
				fire_cooldown = fire_cooldown_max;
				sprite = busy_sprite;
			}
		}
	}

	@Override
	public void draw() {
		Draw.sprite(this);
		if (selected)
			Draw.rect(this, Color.red);
	}

	public HashSet<Target> getTargetsInRange() {
		HashSet<GameObject> returnObjects = new HashSet<>();
		World.retrieveCollidables(returnObjects, new Rectangle(
				(int) (getCenterX() - tile_range * Tile.SIZE),
				(int) (getCenterY() - tile_range * Tile.SIZE),
				(int) (tile_range * Tile.SIZE * 2), (int) (tile_range
						* Tile.SIZE * 2)));
		HashSet<Target> targets = new HashSet<>();
		for (GameObject g : returnObjects) {
			if (!(g instanceof Target))
				continue;
			if (g == this)
				continue;
			Target t = (Target) g;
			if (tileDistance(t) <= tile_range) {
				if (t instanceof Enemy && targetsEnemies)
					targets.add(t);
				else if (t instanceof Tower && !targetsEnemies)
					targets.add(t);
			}
		}
		return targets;
	}

	public float tileDistance(Target target) {
		return (float) Math
				.sqrt(Math.pow(target.getCenterX() - getCenterX(), 2)
						+ Math.pow(target.getCenterY() - getCenterY(), 2))
				/ Tile.SIZE;
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO:
	}

}
