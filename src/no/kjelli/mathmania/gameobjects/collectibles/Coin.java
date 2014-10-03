package no.kjelli.mathmania.gameobjects.collectibles;

import org.newdawn.slick.Color;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Combo;
import no.kjelli.mathmania.gameobjects.blocks.Block;
import no.kjelli.mathmania.gameobjects.particles.GlitterParticle;

public class Coin extends AbstractCollectible {
	private static final int base_x = 0, base_y = 64;
	public static final int SPRITE_SIZE = 16;
	public static final float SCALE = 0.5f;
	public static final float SIZE = SPRITE_SIZE * SCALE;

	public float scoreMultiplier;
	public boolean isCollected = false;

	public Coin(float x, float y, float scoreMultiplier) {
		super(x + Block.SIZE / 2 - SIZE / 2, y + Block.SIZE / 2 - SIZE / 2,
				SIZE, SIZE);
		this.scoreMultiplier = scoreMultiplier;
		sprite = new Sprite(TextureAtlas.objects, base_x, base_y, SPRITE_SIZE,
				SPRITE_SIZE);
		color = new Color(Color.yellow);
		tag(MathMania.tag_playfield);
	}

	@Override
	public int getScore() {
		return (int) (100 * scoreMultiplier);
	}

	@Override
	protected void onCollect() {
		if (!isCollected) {
			isCollected = true;
			Combo.addToCombo(this);
		}
		destroy();
	}

	@Override
	public void update() {
		color.r = (float) Math.sin((float) MathMania.ticks / 100);
		color.g = (float) Math.sin((float) MathMania.ticks / 100 + 2 * Math.PI
				/ 3);
		color.b = (float) Math.sin((float) MathMania.ticks / 100 + 4 * Math.PI
				/ 3);

		if (Math.random() < scoreMultiplier / 500)
			World.add(new GlitterParticle((float) (x + Math.random() * width)
					- width / 2, (float) (y + Math.random() * height) - height
					/ 2, this));
	}

	@Override
	public void draw() {
		Draw.sprite(this, 0f, 0f, 0f, SCALE, SCALE, false);
	}

	@Override
	public int getMultiplier() {
		return 1;
	}

}
