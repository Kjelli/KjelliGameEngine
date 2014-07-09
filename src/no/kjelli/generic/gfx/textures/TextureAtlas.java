package no.kjelli.generic.gfx.textures;

import org.newdawn.slick.opengl.Texture;

public class TextureAtlas {
	public static TextureAtlas tiles = new TextureAtlas("res\\tiles.png");
	public static TextureAtlas objects = new TextureAtlas("res\\objects.png");
	public static TextureAtlas font = new TextureAtlas("res\\fontsmall.png");

	Texture atlasTexture;
	int sourceWidth, sourceHeight;

	private TextureAtlas(Texture texture) {
		this.atlasTexture = texture;
		sourceWidth = texture.getImageWidth();
		sourceHeight = texture.getImageHeight();
	}

	private TextureAtlas(String location) {
		this(Textures.load(location));
	}

	public TextureRegion getTextureRegion(int x, int y, int width, int height) {
		return new TextureRegion(atlasTexture, x, y, width, height);
	}

	public Texture getTextureAtlas() {
		return atlasTexture;
	}

}
