package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.mathmania.MathMania;
import no.kjelli.mathmania.gameobjects.Combo;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.gameobjects.particles.BlockParticle;
import no.kjelli.mathmania.levels.Level;

import org.newdawn.slick.Color;

public abstract class AbstractBlock extends AbstractCollidable implements Block {

	private boolean selected = false;
	protected Color backgroundColor = new Color(0, 0, 0, 0.0f);
	protected Color textColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	public final int x_index, y_index;
	protected final int difficulty;
	protected final int encryptKey;
	protected Level level;
	protected boolean encrypted;

	protected Color encryptedColor = new Color(Color.white);
	protected static final Sprite encryptedSprite = new Sprite(
			TextureAtlas.objects, 160, 32, SPRITE_SIZE, SPRITE_SIZE);

	protected float highlight = 0.0f;

	public AbstractBlock(int x_index, int y_index) {
		this(x_index, y_index, -1, 0);
	}

	public AbstractBlock(int x_index, int y_index, int difficulty,
			int encryptKey) {
		super(x_index * SIZE, y_index * SIZE, SIZE, SIZE);
		this.x_index = x_index;
		this.y_index = y_index;

		this.difficulty = difficulty;
		this.encryptKey = encryptKey;
		encrypted = (encryptKey != 0);

		tag(MathMania.tag_playfield);
	}

	@Override
	public void onCreate() {
		setVisible(true);
		if (difficulty > Level.getDifficulty())
			sprite.setColor(new Color(Color.green));
		else
			sprite.setColor(new Color(Color.white));
	}

	@Override
	public void draw() {
		if (selected) {
			if (highlight < 1.0f) {
				highlight += 0.20;
				sprite.getColor().b += 0.20;
				textColor.a += 0.20;
				textColor.b += 0.20;
				backgroundColor.a += 0.20;
				backgroundColor.b += 0.20;
			}
		} else {
			if (highlight > 0.0f) {
				highlight -= 0.05;
				sprite.getColor().b -= 0.05;
				textColor.a -= 0.05;
				textColor.b -= 0.05;
				backgroundColor.a -= 0.05;
				backgroundColor.b -= 0.05;
			}
		}
		Draw.sprite(sprite, x, y, z);
		// TODO: random glow
		sprite.getColor().a = 0.8f + 0.2f * (float) Math.abs(Math
				.sin((float) (MathMania.ticks + x * y) / 100));

		if (isEncrypted()) {
			encryptedColor.a = 0.5f * (float) Math
					.sin((float) MathMania.ticks / 20) + 0.5f;
			Draw.sprite(encryptedSprite, x, y, 1.1f, 1.0f, xScale, yScale,
					false);
		}

	}

	public abstract String getOperator();

	public void question() {
		MathMania.initQuestion(getQuestion());
	}

	protected abstract Question getQuestion();

	public void select(boolean select) {
		selected = select;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isObstructionBlock() {
		return false;
	}

	public void destroy() {
		super.destroy();
		int particles = 5 * Math.min(Combo.getCount(), 20);
		for (int i = 0; i < particles; i++) {
			World.add(new BlockParticle(getCenterX()
					- BlockParticle.SPRITE_SIZE / 2, getCenterY()
					- BlockParticle.SPRITE_SIZE / 2,
					(float) (i * Math.PI / (particles / 2))));
		}
	}

	public int getEncryptKey() {
		return encryptKey;
	}

	public void decrypt() {
		encrypted = false;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

}
