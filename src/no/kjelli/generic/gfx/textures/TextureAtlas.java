package no.kjelli.generic.gfx.textures;

import org.newdawn.slick.opengl.Texture;

public class TextureAtlas {
	public static TextureAtlas defaultAtlas = new TextureAtlas("res\\atlas.png");
	public static TextureAtlas font = new TextureAtlas("res\\font.png");

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
