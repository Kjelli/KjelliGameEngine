package no.kjelli.bombline.menu;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.input.InputBox;

public class ConnectInput extends InputBox {

	public ConnectInput(float x, float y, float width, float height,
			int maxLength) {
		super(x, y, maxLength);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onLostFocus() {
	}

	@Override
	public void onFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseReleased(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onInputComplete() {
		BombermanOnline.initGame(getText());
	}

}