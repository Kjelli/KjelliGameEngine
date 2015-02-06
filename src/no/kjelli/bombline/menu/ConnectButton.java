package no.kjelli.bombline.menu;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.input.AbstractButton;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

public class ConnectButton extends AbstractButton {
	public static final int width = 100, height = 25;
	private InputConnect connectInput;
	private InputName nameInput;

	public ConnectButton(float x, float y, final InputConnect cInput,
			InputName nInput) {
		super(x, y, width, height, "Connect");
		setHighlight_color(new Color(Color.gray));
		setIdle_color(new Color(Color.black));
		setClick_color(new Color(Color.darkGray));
		this.connectInput = cInput;
		this.nameInput = nInput;
		setInputListener(new InputListener() {
			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_C
						&& Screen.getFocusElement() == null) {
					buttonAction();
				}
			}

			@Override
			public void keyDown(int eventKey) {

			}
		});
	}

	@Override
	public void update() {
	}

	private void buttonAction() {
		BombermanOnline.connect(false);
	}

	@Override
	protected void released(int mouseButton) {
		buttonAction();
	}

	@Override
	protected void clicked(int mouseButton) {
	}

}
