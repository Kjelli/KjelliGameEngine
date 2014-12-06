package no.kjelli.bombline.menu;

import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.input.InputBox;

public class InputConnect extends InputBox {

	public InputConnect(float x, float y) {
		super(x, y, 32, Color.white, Color.black, Color.gray,
				Color.white);
	}

	public InputConnect(float x, float y, float width, float height,
			int maxLength, Color foreground, Color background,
			Color bghighlight, Color fghighlight) {
		super(x, y, maxLength, foreground, background, bghighlight, fghighlight);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
		BombermanOnline.connect(true);
	}

	@Override
	protected String getDefaultText() {
		return "localhost";
	}

}