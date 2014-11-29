package no.kjelli.bombline.menu;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.ScrollingText;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

public class ConnectButton extends Button {
	public static final int width = 100, height = 25;
	private ConnectInput connectInput;

	public ConnectButton(float x, float y, final ConnectInput connectInput) {
		super(x, y, width, height, "Connect");
		setHighlight_color(new Color(Color.gray));
		setIdle_color(new Color(Color.black));
		setClick_color(new Color(Color.darkGray));
		this.connectInput = connectInput;
		Input.register(new InputListener() {
			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_C
						&& Screen.getFocusElement() == null) {
					BombermanOnline.initGame(connectInput.getText());
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

	@Override
	protected void released(int mouseButton) {
		BombermanOnline.initGame(connectInput.getText());
	}

	@Override
	protected void clicked(int mouseButton) {
	}

}
