package no.kjelli.generic.gfx.textures;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class TextureAtlas {

	public static final ArrayList<TextureAtlas> list = new ArrayList<>();
	public static TextureAtlas objects = new TextureAtlas("res\\objects.png");
	public static TextureAtlas partybombs = new TextureAtlas("res\\partybomb.png");
	public static TextureAtlas font = new TextureAtlas("res\\fontsmall.png");

	Texture texture;
	int sourceWidth, sourceHeight;

	private TextureAtlas(Texture texture) {
		this.texture = texture;
		sourceWidth = texture.getImageWidth();
		sourceHeight = texture.getImageHeight();
		list.add(this);
	}

	public TextureAtlas(String location) {
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
		for(TextureAtlas ta : list){
			ta.texture.release();
		}
	}

}
