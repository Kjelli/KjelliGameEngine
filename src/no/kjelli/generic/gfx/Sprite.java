package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import no.kjelli.generic.gfx.textures.SpriteSheet;
import no.kjelli.generic.gfx.textures.TextureRegion;

import org.newdawn.slick.Color;

public class Sprite {

	private static final int LOWER_CASE_OFFSET = (int) ('a' - 'A');
	public static final Color DEFAULT_COLOR = Color.white;
	public static final char[] charList = new char[] { ' ', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'æ', 'ø', 'å', '.',
			',', '?', ':', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'+', '/', '-', '=', '_', '!', '@', '(', ')', '[', ']', '<', '>' };
	public static final Sprite[] chars = initializeFont();
	public static final int CHAR_WIDTH = 8, CHAR_HEIGHT = 8, SPRITE_WIDTH = 32,
			SPRITE_HEIGHT = 2;

	SpriteSheet spritesheet;
	TextureRegion textureRegion;
	VertexBufferObject vbo;
	Color color;
	float width, height;

	public Sprite(SpriteSheet textureAtlas, int texture_x, int texture_y,
			int texture_width, int texture_height) {
		this(textureAtlas, textureAtlas.getTextureRegion(texture_x, texture_y,
				texture_width, texture_height));
	}

	public Sprite(Sprite sprite) {
		this(sprite.spritesheet, sprite.spritesheet
				.getTextureRegion(sprite.textureRegion.getU(), sprite
						.getTextureRegion().getV(), sprite.textureRegion
						.getU2(), sprite.getTextureRegion().getV2()));

	}

	public Sprite(SpriteSheet textureAtlas, TextureRegion textureRegion) {
		this.spritesheet = textureAtlas;
		this.textureRegion = textureRegion;
		this.vbo = VertexBufferObject.create(textureRegion.getWidth(),
				textureRegion.getHeight(), getTextureRegion().getU(),
				textureRegion.getU2(), textureRegion.getV(),
				textureRegion.getV2());
		this.width = textureRegion.getWidth();
		this.height = textureRegion.getHeight();
		this.color = new Color(DEFAULT_COLOR);
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	public VertexBufferObject getVertexBufferObject() {
		return vbo;
	}

	public void setTextureCoords(int texture_x, int texture_y,
			int sprite_width, int sprite_height) {
		textureRegion.set(spritesheet.getTextureAtlas(), texture_x, texture_y,
				sprite_width, sprite_height);
		vbo.setTextureCoords(textureRegion.getU(), textureRegion.getU2(),
				textureRegion.getV(), textureRegion.getV2());
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public static Sprite resolveChar(char charAt) {
		if (charAt > 64 & charAt < 91)
			charAt += LOWER_CASE_OFFSET;

		for (int i = 0; i < charList.length; i++) {
			if (charAt == charList[i]) {
				return chars[i];
			}
		}
		return chars[0];
	}

	private static Sprite[] initializeFont() {
		Sprite[] temp = new Sprite[charList.length];
		for (int i = 0; i < charList.length; i++) {
			int x = (i % SPRITE_WIDTH) * CHAR_WIDTH;
			int y = (SPRITE_HEIGHT - 1) * CHAR_HEIGHT - (i / SPRITE_WIDTH)
					* CHAR_HEIGHT;
			temp[i] = new Sprite(SpriteSheet.font, x, y, CHAR_WIDTH,
					CHAR_HEIGHT);
		}
		return temp;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void destroy() {
		glDeleteBuffers(vbo.getVertexHandle());
		glDeleteBuffers(vbo.getTextureHandle());
	}

}
