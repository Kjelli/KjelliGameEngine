package no.kjelli.bombline.menu;

import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
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

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
	}

}