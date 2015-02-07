package no.kjelli.bombline.menu;

import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;

import no.kjelli.generic.input.AbstractInputBox;

public class InputName extends AbstractInputBox {
	private static final String[] names = { "Kjell", "Rolf", "Vigdis",
			"Michelle", "Robert", "Nils", "Høna", "Ingve", "Biffen", "Leif",
			"Bodil", "Svein", "Jesus", "Pornofrans", "Susan" };

	public InputName(float x, float y) {
		super(x, y, 10, Color.white, Color.black, Color.gray, Color.white);
		setSymbolsAllowed(false);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	protected void onInputComplete() {
	}

	@Override
	protected String getDefaultText() {
		return names[(int) (Math.random() * names.length)];
	}

}