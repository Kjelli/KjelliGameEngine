package no.kjelli.towerdefense.gameobjects.towers;

import org.newdawn.slick.Color;

import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.map.Tile;

public abstract class Tower extends AbstractCollidable{
	
	protected Tile tile;
	int x_index, y_index;
	public boolean selected;
	
	public Tower(Tile tile){
		super(0,0,Tile.SIZE,Tile.SIZE);
		this.tile = tile;
		this.tile.tower = this;
		this.x_index = tile.x_index;
		this.y_index = tile.y_index;
		x = tile.getX();
		y = tile.getY();
	}
	
	public abstract void shoot(float x, float y);
	public abstract void shoot(Enemy e);
	public abstract void shoot(Tower t);
	public abstract void afterShoot();
	
	public void onSelect(){	
		// TODO:
	}

	public void onUnselect() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		Draw.sprite(this);
		if(selected)
			Draw.rect(this, Color.red);
	}
	
	@Override
	public void onCollision(Collision collision) {
		// TODO:
	}
	
	
}
