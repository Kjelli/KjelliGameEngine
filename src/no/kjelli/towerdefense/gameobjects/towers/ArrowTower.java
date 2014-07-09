package no.kjelli.towerdefense.gameobjects.towers;

import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.towerdefense.TowerDefense;
import no.kjelli.towerdefense.gameobjects.enemies.Enemy;
import no.kjelli.towerdefense.map.Map;

public class ArrowTower extends Tower {

	static final int COOLDOWN_MAX = 30;
	int cooldown = 0;

	Sprite idle_sprite;
	Sprite busy_sprite;

	public ArrowTower(Map map, int x_index, int y_index) {
		super(map.getTile(x_index, y_index));
	}

	@Override
	public void onCreate() {
		idle_sprite = new Sprite(TextureAtlas.objects, 0, 0, 32, 32);
		busy_sprite = new Sprite(TextureAtlas.objects, 32, 0, 32, 32);
		sprite = idle_sprite;
		setVisible(true);
	}

	@Override
	public void update() {
		if (cooldown > 0) {
			cooldown--;
			if (cooldown == 0)
				sprite = idle_sprite;
		}
		
		if(TowerDefense.ticks % 100 == 0){
			shoot(0,0);
		}
	}

	@Override
	public void shoot(float x, float y) {
		afterShoot();
	}

	@Override
	public void shoot(Enemy e) {
		afterShoot();
	}

	@Override
	public void shoot(Tower t) {
		afterShoot();
	}

	@Override
	public void afterShoot() {
		SoundPlayer.play("bounce");
		sprite = busy_sprite;
		cooldown = COOLDOWN_MAX;
	}

}
