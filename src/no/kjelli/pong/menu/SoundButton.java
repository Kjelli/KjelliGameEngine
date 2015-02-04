package no.kjelli.pong.menu;

import org.newdawn.slick.Color;

import no.kjelli.generic.input.AbstractButton;

public class SoundButton extends AbstractButton {
	public final static float WIDTH = 100, HEIGHT = 40;
	private static final int COOLDOWN_MAX = 10;

	private final ControlInput parent;
	private int cooldown = COOLDOWN_MAX;

	public SoundButton(float x, float y, ControlInput parent) {
		super(x, y, WIDTH, HEIGHT, "Sound", new Color(Color.darkGray),
				new Color(Color.black), new Color(Color.gray));
		this.parent = parent;
	}

	@Override
	protected void released(int mouseButton) {
		if (cooldown == 0)
			parent.complete();
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
