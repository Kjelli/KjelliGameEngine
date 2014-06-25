package no.kjelli.generic.gfx.textures;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import no.kjelli.generic.gfx.VertexBufferObject;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	public static final int CHARWIDTH = 16, CHARHEIGHT = 20;

	TextureAtlas textureAtlas;
	TextureRegion textureRegion;
	VertexBufferObject vbo;

	public static final char[] charList = new char[] { '`', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '´',
			' ', ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '[', '\\', ']', '^', '_', ' ', '!', '"', '#', '£', '%',
			'&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
			' ', ' ', ' ', ' ', ' ', ' ', };
	public static final Sprite[] chars = initializeFont();

	private static Sprite[] initializeFont() {
		Sprite[] temp = new Sprite[128];
		for (int i = 0; i < charList.length; i++) {
			int x = (i % 32) * CHARWIDTH;
			int y = 52 - (i / 32) * CHARHEIGHT + (i / 32) * 2;
			temp[i] = new Sprite(TextureAtlas.font, x, y, CHARWIDTH, CHARHEIGHT);
		}
		return temp;
	}

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
		textureRegion.texture.release();
		glDeleteBuffers(vbo.getVertexHandle());
		glDeleteBuffers(vbo.getTextureHandle());

	}

	public void setSpriteInAtlas(int texture_x, int texture_y,
			int texture_width, int texture_height) {
		textureRegion.set(textureAtlas.getTextureAtlas(), texture_x, texture_y,
				texture_width, texture_height);
		vbo.setTextureCoords(textureRegion.getU(), textureRegion.getU2(),
				textureRegion.getV(), textureRegion.getV2());
	}

	public static Sprite resolveChar(char charAt) {
		for (int i = 0; i < charList.length; i++) {
			if (charAt == charList[i]) {
				return chars[i];
			}
		}
		return chars[0];
	}
}
