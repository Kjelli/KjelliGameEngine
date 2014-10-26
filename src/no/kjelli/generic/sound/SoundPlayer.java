package no.kjelli.generic.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundPlayer {

	private static final int COOLDOWN_MAX = 3;
	private static int play_cooldown;
	private static HashMap<String, UnoptimizedSound> sounds = new HashMap<>();

	public static void load(String path) throws IOException {
		if (ResourceLoader.resourceExists(path = "res\\sfx\\" + path)) {
			InputStream stream = ResourceLoader.getResourceAsStream(path);
			String[] elements = path.toString().split("[\\\\.]");
			String name = elements[elements.length - 2];
			String format = elements[elements.length - 1].toUpperCase();
			UnoptimizedSound sound = new UnoptimizedSound(AudioLoader.getAudio(
					format, stream));
			sounds.put(name, sound);
		} else
			throw new IOException("Sound not found");
	}

	public static void play(String soundName) {
		play(soundName, 1.0f, 1.0f, false);
	}

	public static void play(String soundName, float pitch) {
		play(soundName, pitch, 1.0f, false);
	}

	public static void play(String soundName, float pitch, float gain) {
		play(soundName, pitch, gain, false);
	}

	public static void play(String soundName, float pitch, float gain,
			boolean loop) {
		if (!sounds.containsKey(soundName)) {
			System.err.println("Sound not found or loaded!");
			return;
		}
		Audio soundToPlay = sounds.get(soundName).getAudio();
		if (play_cooldown > 0 && soundToPlay.isPlaying())
			return;
		sounds.get(soundName).play(pitch, gain, loop);
		play_cooldown = COOLDOWN_MAX;
	}

	public static void music(String soundName) {
		music(soundName, 1.0f, 1.0f);
	}

	public static void music(String soundName, float pitch) {
		music(soundName, pitch, 1.0f);
	}

	public static void music(String soundName, float pitch, float gain) {
		sounds.get(soundName).music(pitch, gain);
	}

	static boolean isTime = false;

	public static void update() {
		if (play_cooldown > 0)
			play_cooldown--;
	}

	public static void setGain(String soundName, float gain) {
		sounds.get(soundName).setGain(gain);

	}

	public static void sync(String soundName, String soundName2) {
		sounds.get(soundName).getAudio()
				.setPosition(sounds.get(soundName2).getAudio().getPosition());
	}
}
