package no.kjelli.generic.gfx.textures;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class SpriteSheet {

<<<<<<< HEAD:src/no/kjelli/generic/gfx/textures/SpriteSheet.java
	public static final HashSet<SpriteSheet> list = new HashSet<>();
	public static SpriteSheet font = new SpriteSheet("res\\fontsmall.png");
=======
	public static final ArrayList<TextureAtlas> list = new ArrayList<>();
	public static TextureAtlas objects = new TextureAtlas("res\\objects.png");
	public static TextureAtlas partybombs = new TextureAtlas("res\\partybomb.png");
	public static TextureAtlas font = new TextureAtlas("res\\fontsmall.png");
>>>>>>> origin/Misc:src/no/kjelli/generic/gfx/textures/TextureAtlas.java

	Texture texture;
	int sourceWidth, sourceHeight;

<<<<<<< HEAD:src/no/kjelli/generic/gfx/textures/SpriteSheet.java
	private SpriteSheet(Texture texture) {
		this.atlasTexture = texture;
=======
	private TextureAtlas(Texture texture) {
		this.texture = texture;
>>>>>>> origin/Misc:src/no/kjelli/generic/gfx/textures/TextureAtlas.java
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
<<<<<<< HEAD:src/no/kjelli/generic/gfx/textures/SpriteSheet.java
		for (SpriteSheet ta : list) {
			ta.atlasTexture.release();
=======
		for(TextureAtlas ta : list){
			ta.texture.release();
>>>>>>> origin/Misc:src/no/kjelli/generic/gfx/textures/TextureAtlas.java
		}
	}

}
