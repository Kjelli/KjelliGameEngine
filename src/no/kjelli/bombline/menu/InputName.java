package no.kjelli.bombline.menu;

import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
<<<<<<< HEAD
import no.kjelli.generic.input.InputBox;

<<<<<<< HEAD
public class InputName extends AbstractInputBox {
=======
public class InputName extends InputBox {
	private static final String[] names = { "Kjell", "Rolf", "Vigdis",
			"Michelle", "Robert", "Nils", "Høna", "Ingve", "Biffen", "Leif",
			"Bodil", "Svein", "Jesus", "Pornofrans", "Susan" };
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene

	public InputName(float x, float y, float width, float height, int maxLength) {
		super(x, y, maxLength, "Kjell", Color.white, Color.black, Color.gray,
				Color.white);
		setSymbolsAllowed(false);
	}

	public InputName(float x, float y, float width, float height,
			int maxLength, Color foreground, Color background,
			Color bghighlight, Color fghighlight) {
		super(x, y, maxLength, "Kjell", foreground, background, bghighlight,
				fghighlight);
	}

=======
import no.kjelli.generic.input.AbstractInputBox;

public class InputName extends AbstractInputBox {
	private static final String[] names = { "Kjell", "Rolf", "Vigdis",
			"Michelle", "Robert", "Nils", "Høna", "Ingve", "Biffen", "Leif",
			"Bodil", "Svein", "Jesus", "Pornofrans", "Susan" };

	public InputName(float x, float y) {
		super(x, y, 10, Color.white, Color.black, Color.gray, Color.white);
		setSymbolsAllowed(false);
	}

>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
	}

<<<<<<< HEAD
=======
	@Override
	protected String getDefaultText() {
		return names[(int) (Math.random() * names.length)];
	}

>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
}