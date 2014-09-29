package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.generic.gfx.textures.TextureRegion;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	public static final int CHAR_WIDTH = 8, CHAR_HEIGHT = 8, SPRITE_WIDTH = 32,
			SPRITE_HEIGHT = 2;

	public static final char[] charList = new char[] { ' ', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'æ', 'ø', 'å', '.',
			',', '?', ':', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'+', '/', '-', '=', '_' };
	public static final Sprite[] chars = initializeFont();

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

	TextureAtlas textureAtlas;
	TextureRegion textureRegion;
	VertexBufferObject vbo;

	public Sprite(Texture texture, float width, float height) {
		vbo = VertexBufferObject.create(VertexBufferObject.RECTANGLE, width,
				height);
		textureRegion = new TextureRegion(texture);
	}

	public Sprite(TextureAtlas textureAtlas, int texture_x, int texture_y,
			int texture_width, int texture_height) {
		this.textureAtlas = textureAtlas;
		textureRegion = textureAtlas.getTextureRegion(texture_x, texture_y,
				texture_width, texture_height);
		vbo = VertexBufferObject.create(texture_width, texture_height,
				textureRegion.getU(), textureRegion.getU2(),
				textureRegion.getV(), textureRegion.getV2());
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

	public void setSpriteInAtlas(int texture_x, int texture_y,
			int sprite_width, int sprite_height) {
		textureRegion.set(textureAtlas.getTextureAtlas(), texture_x, texture_y,
				sprite_width, sprite_height);
		vbo.setTextureCoords(textureRegion.getU(), textureRegion.getU2(),
				textureRegion.getV(), textureRegion.getV2());
	}

	static final int LOWER_CASE_OFFSET = (int) ('a' - 'A');

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
}
