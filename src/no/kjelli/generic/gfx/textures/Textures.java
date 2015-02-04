package no.kjelli.generic.gfx.textures;

import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	public static Texture load(String filename) {
		Texture texture = null;
		String[] elements = filename.split("[\\\\.]");
		String format = elements[elements.length - 1].toUpperCase();

		try {
			texture = TextureLoader.getTexture(format,
					ResourceLoader.getResourceAsStream(filename), GL_NEAREST); //GL_LINEAR
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;

	}
}
