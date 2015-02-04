package no.kjelli.generic.gfx.textures;

import java.util.HashSet;

import org.newdawn.slick.opengl.Texture;

public class SpriteSheet {

	public static final HashSet<SpriteSheet> list = new HashSet<>();
	public static SpriteSheet font = new SpriteSheet("res\\fontsmall.png");

	Texture texture;
	int sourceWidth, sourceHeight;

	private SpriteSheet(Texture texture) {
		this.texture = texture;

		sourceWidth = texture.getImageWidth();
		sourceHeight = texture.getImageHeight();
		list.add(this);
	}

	public SpriteSheet(String location) {
		this(Textures.load(location));
	}

	public TextureRegion getTextureRegion(int x, int y, int width, int height) {
		return new TextureRegion(texture, x, y, width, height);
	}

	public TextureRegion getTextureRegion(float u, float u2, float v, float v2) {
		return new TextureRegion(texture, u, u2, v, v2);
	}

	public Texture getTextureAtlas() {
		return texture;
	}

	public static void destroy() {
		for (SpriteSheet ta : list) {
			ta.texture.release();
		}
	}

}
