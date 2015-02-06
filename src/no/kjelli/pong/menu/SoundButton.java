package no.kjelli.pong.menu;

import org.newdawn.slick.Color;

import no.kjelli.generic.input.AbstractButton;
import no.kjelli.generic.settings.Settings;

public class SoundButton extends AbstractButton {
	public final static float WIDTH = 100, HEIGHT = 40;
	private static final int COOLDOWN_MAX = 10;

	private int cooldown = COOLDOWN_MAX;

	public SoundButton(float x, float y) {
		super(x, y, WIDTH, HEIGHT, "Sound: on", new Color(Color.darkGray),
				new Color(Color.black), new Color(Color.gray));
		Settings.put("sound_mute", false);
	}

	@Override
	protected void released(int mouseButton) {
		if (cooldown == 0) {
			boolean mute = Settings.get("sound_mute", false);
			if (!mute) {
				setText("Sound: off");
				Settings.put("sound_mute", true);
			} else {
				setText("Sound: on");
				Settings.put("sound_mute", false);
			}
		}
	}

	@Override
	protected void clicked(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		if (cooldown > 0)
			cooldown--;
	}

}
