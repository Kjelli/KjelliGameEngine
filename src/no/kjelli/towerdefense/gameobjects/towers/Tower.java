package no.kjelli.towerdefense.gameobjects.towers;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.map.Map;
import no.kjelli.towerdefense.map.Tile;

public abstract class Tower extends AbstractGameObject{
	protected Map map;
	int x_index, y_index;
	
	public Tower(Map map, int x_index, int y_index){
		super(0,0,Tile.SIZE,Tile.SIZE);
		this.x_index = x_index;
		this.y_index = y_index;
		x = map.getX() + x_index * Tile.SIZE;
		y = map.getY() + y_index * Tile.SIZE;
	}
	
	public abstract void shoot(float x, float y);
	public abstract void shoot(Enemy e);
	public abstract void shoot(Tower t);
	public abstract void afterShoot();
	
	
}
