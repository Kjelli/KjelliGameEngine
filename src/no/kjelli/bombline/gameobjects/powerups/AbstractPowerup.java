package no.kjelli.bombline.gameobjects.powerups;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.gameobjects.PlayerMP;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.PacketPowerupGain;
import no.kjelli.bombline.network.PacketPowerupSpawn;
import no.kjelli.generic.Collision;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.sound.SoundPlayer;

public abstract class AbstractPowerup extends AbstractCollidable implements
		Powerup {
	private static Sprite background = new Sprite(TextureAtlas.partybombs,
			BACKGROUND_BASE_X, BACKGROUND_BASE_Y, SPRITE_WIDTH, SPRITE_HEIGHT);
	public static final int blow_x = 224, blow_y = 48;
	private static final int BLOW_TIMER_MAX = 4;
	private static final int BLOW_COOLDOWN_MAX = 20;

	public int blow_cooldown = BLOW_COOLDOWN_MAX;
	public int blow_frame = 3;
	public int blow_timer = BLOW_TIMER_MAX;
	private int animation_frame = 0;
	public boolean destroying = false;

	private int x_index, y_index;
	private int type;

	public AbstractPowerup(int x_index, int y_index, float z, int type) {
		super(x_index * BombermanOnline.block_size + BombermanOnline.block_size
				/ 2 - 6, y_index * BombermanOnline.block_size
				+ BombermanOnline.block_size / 2 - 6, 1f, 12, 12);
		this.x_index = x_index;
		this.y_index = y_index;
		this.type = type;
		tag(BombermanOnline.tag_playfield);
	}

	public void onCollision(Collision collision) {
		if (!isVisible)
			return;
		Collidable target = collision.getTarget();
		if (target instanceof Player) {
			if (Network.isHosting()) {
				Player player = (Player) target;
				if (player instanceof PlayerMP) {
					PlayerMP pmp = (PlayerMP) player;
					Network.getServer().sendToAllTCP(
							new PacketPowerupGain(pmp.getID(), type, x_index,
									y_index));
				} else {
					Network.getServer().sendToAllTCP(
							new PacketPowerupGain(Network.getClient().getID(),
									type, x_index, y_index));
					setVisible(false);
				}
			} else {
				// Clientside view - make powerup disappear and wait for packet.
				setVisible(false);
			}
		}
	}

	public void collect(Player player) {
		SoundPlayer.play("sound10 powerup");
		powerUpEffect(player);
		destroy();
	}

	@Override
	public void onCreate() {
		setVisible(true);
		if (Network.isOnline() && Network.isHosting()) {
			Network.getServer().sendToAllExceptTCP(Network.getClient().getID(),
					new PacketPowerupSpawn(type, x_index, y_index));
		}
	}

	@Override
	public void draw() {
		if (!destroying)
			Draw.sprite(background, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z, 0,
					1.0f, 1.0f, false, false, false);
		Draw.sprite(sprite, x + DRAW_X_OFFSET, y + DRAW_Y_OFFSET, z + 0.1f, 0,
				1.0f, 1.0f, false, false, false);
	}

	@Override
	public void update() {
		if (blow_cooldown > 0)
			blow_cooldown--;
		if (destroying) {
			if (blow_timer > 0) {
				blow_timer--;
			} else {
				blow_timer = BLOW_TIMER_MAX;
				if (blow_frame > 0) {
					blow_frame--;
					sprite.setTextureCoords(blow_x, blow_y + blow_frame
							* SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
				} else
					destroy();
			}
		}

		if (BombermanOnline.ticks % ANIMATION_TIMER == 0) {
			animation_frame = (animation_frame + 1) % ANIMATION_FRAMES;
			background.setTextureCoords(BACKGROUND_BASE_X + animation_frame
					* SPRITE_WIDTH, BACKGROUND_BASE_Y, SPRITE_WIDTH,
					SPRITE_HEIGHT);
		}
	}

	public void blowUp() {
		if (blow_cooldown > 0)
			return;
		if (!destroying) {
			sprite.setTextureCoords(blow_x, blow_y, SPRITE_WIDTH, SPRITE_HEIGHT);
			destroying = true;
		}
	}

	public int getType() {
		return type;
	}

}
