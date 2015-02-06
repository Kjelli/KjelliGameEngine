package no.kjelli.generic.gfx.textures;

import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	public static Texture load(String filename) {
		return load(filename, GL_NEAREST);
	}
	public static Texture load(String filename, int quality) {
		Texture texture = null;
		String[] elements = filename.split("[\\\\.]");
		String format = elements[elements.length - 1].toUpperCase();

		try {
			texture = TextureLoader.getTexture(format,
					ResourceLoader.getResourceAsStream(filename), quality); //GL_LINEAR | GL_NEAREST
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;

	}
}
