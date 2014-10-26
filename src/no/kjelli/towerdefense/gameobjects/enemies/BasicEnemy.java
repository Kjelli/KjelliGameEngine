package no.kjelli.towerdefense.gameobjects.enemies;

import no.kjelli.generic.gfx.Draw;

public class BasicEnemy extends Enemy{
	public final static float SIZE = 16;

	public BasicEnemy(float x, float y) {
		super(x, y, SIZE, SIZE);
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
		Draw.sprite(this);
		
	}

}
