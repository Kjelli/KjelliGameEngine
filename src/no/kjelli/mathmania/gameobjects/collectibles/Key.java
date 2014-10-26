package no.kjelli.mathmania.gameobjects.collectibles;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Combo;
import no.kjelli.mathmania.gameobjects.Score;
import no.kjelli.mathmania.gameobjects.blocks.Block;
import no.kjelli.mathmania.gameobjects.particles.EncryptionParticle;
import no.kjelli.mathmania.gameobjects.particles.GlitterParticle;
import no.kjelli.mathmania.levels.Level;

public class Key extends AbstractCollectible {
	private static final int base_x = 48, base_y = 80;
	public static final int SPRITE_SIZE = 16;
	public static final float SIZE = SPRITE_SIZE * 0.5f;

	public static final int NUM_PARTICLES = 20;

	public boolean isCollected = false;

	protected final int encryptKey;

	public Key(float x, float y, int encryptKey) {
		super(x + Block.SIZE / 2 - SIZE / 2, y + Block.SIZE / 2 - SIZE / 2,
				SIZE, SIZE);
		this.encryptKey = encryptKey;
		xScale = 0.5f;
		yScale = 0.5f;
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
		tag(MathMania.tag_playfield);
	}

	@Override
	public int getScore() {
		return 100;
	}

	@Override
	protected void onCollect() {
		isCollected = true;
		Score.addToScore(getScore());
		SoundPlayer.play("coin", ((float) Combo.getCount() / 30) + 1.0f);
		Combo.addToCombo(this);
		if (encryptKey != 0) {
			Level.decrypt(encryptKey);
			for (int i = 0; i < NUM_PARTICLES; i++) {
				World.add(new EncryptionParticle(x, y,
						(float) (i * Math.PI / ((float) NUM_PARTICLES / 2))));
			}
		}
		destroy();
	}

	@Override
	public void update() {
		sprite.getColor().r = (float) Math.sin((float) MathMania.ticks / 100 + x * y);
		sprite.getColor().g = (float) Math.sin((float) MathMania.ticks / 100 + x * y + 2
				* Math.PI / 3);
		sprite.getColor().b = (float) Math.sin((float) MathMania.ticks / 100 + x * y + 4
				* Math.PI / 3);

		if (Math.random() < 0.001f && Screen.contains(this))
			World.add(new GlitterParticle((float) (x + Math.random() * width)
					- width / 2, (float) (y + Math.random() * height) - height
					/ 2, this));
	}

	@Override
	public void draw() {
		Draw.drawable(this, 0f, 0f, 0f, 0f, false);
	}

	@Override
	public int getMultiplier() {
		return 1;
	}

}
