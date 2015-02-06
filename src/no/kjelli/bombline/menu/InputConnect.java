package no.kjelli.bombline.menu;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.input.InputBox;

<<<<<<< HEAD
import org.newdawn.slick.Color;

public class InputConnect extends AbstractInputBox {
=======
public class InputConnect extends InputBox {
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene

	public InputConnect(float x, float y, float width, float height,
			int maxLength) {
		super(x, y, maxLength,"localhost", Color.white, Color.black, Color.gray,
				Color.white);
	}

	public InputConnect(float x, float y, float width, float height,
			int maxLength, Color foreground, Color background,
			Color bghighlight, Color fghighlight) {
		super(x, y, maxLength, "localhost", foreground, background,
				bghighlight, fghighlight);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
		BombermanOnline.connect(true);
	}

}