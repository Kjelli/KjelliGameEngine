package no.kjelli.pong.menu;

import org.newdawn.slick.Color;

import no.kjelli.generic.input.AbstractInputBox;

public class NameInputBox extends AbstractInputBox {
	public static final Color foreground = new Color(Color.white),
			background = new Color(Color.black), bghighlight = new Color(
					Color.darkGray), fghighlight = new Color(Color.white);
	public static final int MAX_CAP = 9;

	public NameInputBox(String defaultname, float x, float y) {
		super(x, y, MAX_CAP, defaultname, foreground, background, bghighlight, fghighlight);

	}

	@Override
	public void onCreate() {
		setVisible(true);
	}


	@Override
	protected void onInputComplete() {
	}

}
