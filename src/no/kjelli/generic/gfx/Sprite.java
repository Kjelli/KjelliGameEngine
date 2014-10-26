package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.gfx.textures.TextureRegion;

import org.newdawn.slick.Color;

public class Sprite {

	private static final int LOWER_CASE_OFFSET = (int) ('a' - 'A');
	public static final Color DEFAULT_COLOR = Color.white;
	public static final char[] charList = new char[] { ' ', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'æ', 'ø', 'å', '.',
			',', '?', ':', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'+', '/', '-', '=', '_' };

	private static Sprite[] initializeFont() {
		Sprite[] temp = new Sprite[charList.length];
		for (int i = 0; i < charList.length; i++) {
			int x = (i % SPRITE_WIDTH) * CHAR_WIDTH;
			int y = (SPRITE_HEIGHT - 1) * CHAR_HEIGHT - (i / SPRITE_WIDTH)
					* CHAR_HEIGHT;
			temp[i] = new Sprite(TextureAtlas.font, x, y, CHAR_WIDTH,
					CHAR_HEIGHT);
		}
		return temp;
	}

	public static final Sprite[] chars = initializeFont();
	public static final int CHAR_WIDTH = 8, CHAR_HEIGHT = 8, SPRITE_WIDTH = 32,
			SPRITE_HEIGHT = 2;

	TextureAtlas textureAtlas;
	TextureRegion textureRegion;
	VertexBufferObject vbo;
	Color color;
	float width, height;

	public Sprite(TextureAtlas textureAtlas, int texture_x, int texture_y,
			int texture_width, int texture_height) {
		this.textureAtlas = textureAtlas;
		this.textureRegion = textureAtlas.getTextureRegion(texture_x,
				texture_y, texture_width, texture_height);
		this.vbo = VertexBufferObject.create(texture_width, texture_height,
				textureRegion.getU(), textureRegion.getU2(),
				textureRegion.getV(), textureRegion.getV2());
		this.width = texture_width;
		this.height = texture_height;
		this.color = new Color(DEFAULT_COLOR);
	}

	public Sprite(Sprite sprite) {
		this.textureAtlas = sprite.textureAtlas;
		this.textureRegion = textureAtlas
				.getTextureRegion(sprite.textureRegion.getU(), sprite
						.getTextureRegion().getV(), sprite.textureRegion
						.getU2(), sprite.getTextureRegion().getV2());
		this.vbo = VertexBufferObject.create(sprite.getTextureRegion()
				.getWidth(), sprite.textureRegion.getHeight(),
				getTextureRegion().getU(), textureRegion.getU2(), textureRegion
						.getV(), textureRegion.getV2());
		this.width = sprite.textureRegion.getWidth();
		this.height = sprite.textureRegion.getHeight();
		this.color = new Color(sprite.getColor());
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	public VertexBufferObject getVertexBufferObject() {
		return vbo;
	}

	public void destroy() {
		glDeleteBuffers(vbo.getVertexHandle());
		glDeleteBuffers(vbo.getTextureHandle());
	}

	public void setTextureCoords(int texture_x, int texture_y,
			int sprite_width, int sprite_height) {
		textureRegion.set(textureAtlas.getTextureAtlas(), texture_x, texture_y,
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

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
