package no.kjelli.bombline.menu;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.input.AbstractButton;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

public class HostButton extends AbstractButton {
	public static final int width = 100, height = 50;

	public HostButton(float x, float y) {
		super(x, y, width, height, "Host");
		setHighlight_color(new Color(Color.gray));
		setIdle_color(new Color(Color.black));
		setClick_color(new Color(Color.darkGray));
		setInputListener(new InputListener() {
			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_H
						&& Screen.getFocusElement() == null) {
					BombermanOnline.connect(true);
				}
			}

			@Override
			public void keyDown(int eventKey) {
			}
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void released(int mouseButton) {
		BombermanOnline.connect(true);
	}

	@Override
	protected void clicked(int mouseButton) {
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
