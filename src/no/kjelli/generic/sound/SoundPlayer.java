package no.kjelli.generic.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.openal.*;
import org.newdawn.slick.util.ResourceLoader;

public class SoundPlayer {

	private static final int COOLDOWN_MAX = 3;
	private static int cooldown;

	private static HashMap<String, Audio> sounds = new HashMap<>();

	public static void load(String path) throws IOException {
		if (ResourceLoader.resourceExists(path)) {
			InputStream stream = ResourceLoader.getResourceAsStream(path);
			String[] elements = path.toString().split("[\\\\.]");
			String name = elements[elements.length - 2];
			String format = elements[elements.length - 1].toUpperCase();
			Audio audio = AudioLoader.getAudio(format, stream);
			sounds.put(name, audio);
		} else
			throw new IOException("Sound not found");
	}

	public static void play(String soundName) {
		play(soundName, 1.0f, 1.0f);
	}

	public static void play(String soundName, float pitch) {
		play(soundName, pitch, 1.0f);
	}

	public static void play(String soundName, float pitch, float gain) {
		if (!sounds.containsKey(soundName)) {
			System.err.println("Sound not found or loaded!");
			return;
		}
		
		if(cooldown > 0)
			return;
		
		sounds.get(soundName).playAsSoundEffect(pitch, gain, false);
		cooldown = COOLDOWN_MAX;
	}

	public static void update() {
		if (cooldown > 0)
			cooldown--;
	}
}
