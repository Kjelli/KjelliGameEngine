package no.kjelli.generic.sound;

import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.alSourcef;

import org.newdawn.slick.openal.Audio;

public class UnoptimizedSound {
	private Audio audio;
	private int id;

	public UnoptimizedSound(Audio audio) {
		this.audio = audio;
	}

	public int getId() {
		return id;
	}

	public Audio getAudio() {
		return audio;
	}

	public void play(float pitch, float gain, boolean loop) {
		id = audio.playAsSoundEffect(pitch, Math.min(1.0f, gain), loop);
	}

	public void music(float pitch, float gain) {
		id = getAudio().playAsMusic(pitch, gain, true);
	}

	public void setGain(float gain) {
		alSourcef(id, AL_GAIN, Math.max(0.0f, Math.min(1.0f, gain)));
	}

	public void sync(UnoptimizedSound sync) {

	}
}
