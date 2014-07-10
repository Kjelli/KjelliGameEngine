package no.kjelli.towerdefense.gameobjects.projectiles;

import org.newdawn.slick.Color;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.towerdefense.TowerDefense;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.gameobjects.towers.Tower;

public class StarProjectile extends Projectile {

	private static final int base_x = 0, base_y = 224;
	public static final int SIZE = 8;
	public static final float SPEED = 2.0f;

	public StarProjectile(float x, float y) {
		super(x, y, SIZE, SIZE, SPEED);
		sprite = new Sprite(TextureAtlas.objects, 0, 224, 8, 8);
		color = Color.white;
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollide(Enemy target) {
		if (this.target.equals(target))
			destroy();
	}

	@Override
	public void onCollide(Tower target) {
		if (this.target.equals(target))
			destroy();
	}

	@Override
	public void update() {
		super.update();
		move();

		sprite.setSpriteInAtlas(base_x + (int) ((TowerDefense.ticks / 10) % 2)
				* 8, base_y, 8, 8);

		if (target == null)
			destroy();
	}

	@Override
	public void draw() {
		Draw.sprite(this, 0f, 0f, (frames*2)%360, 2.0f, 2.0f, false);
	}

}
