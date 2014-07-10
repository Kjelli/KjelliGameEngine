package no.kjelli.towerdefense.gameobjects.towers;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.towerdefense.gameobjects.projectiles.StarProjectile;
import no.kjelli.towerdefense.gameobjects.projectiles.Target;
import no.kjelli.towerdefense.map.Map;

public class ArrowTower extends Tower {

	static final int COOLDOWN_MAX = 30;
	static final float RANGE =  10.0f;

	public ArrowTower(Map map, int x_index, int y_index) {
		super(map.getTile(x_index, y_index), RANGE, false, COOLDOWN_MAX);
	}

	@Override
	public void onCreate() {
		idle_sprite = new Sprite(TextureAtlas.objects, 0, 0, 32, 32);
		busy_sprite = new Sprite(TextureAtlas.objects, 32, 0, 32, 32);
		sprite = idle_sprite;
		setVisible(true);
	}

	@Override
	public void shoot(Target t) {
		StarProjectile sp = new StarProjectile(getCenterX()
				- StarProjectile.SIZE / 2, getCenterY() - StarProjectile.SIZE
				/ 2);
		sp.setTarget(t);
		World.add(sp, World.FOREGROUND);
		afterShoot();
	}

	@Override
	public void afterShoot() {
		SoundPlayer.play("bounce");
		sprite = busy_sprite;
	}

}
