package no.kjelli.bombline.menu;

<<<<<<< HEAD
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
=======
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.input.AbstractInputBox;

public class InputConnect extends AbstractInputBox {

	public InputConnect(float x, float y) {
		super(x, y, 32, Color.white, Color.black, Color.gray,
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
				Color.white);
	}

	public InputConnect(float x, float y, float width, float height,
			int maxLength, Color foreground, Color background,
			Color bghighlight, Color fghighlight) {
<<<<<<< HEAD
		super(x, y, maxLength, "localhost", foreground, background,
				bghighlight, fghighlight);
=======
		super(x, y, maxLength, foreground, background, bghighlight, fghighlight);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
		BombermanOnline.connect(true);
	}

<<<<<<< HEAD
=======
	@Override
	protected String getDefaultText() {
		return "localhost";
	}

>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
}