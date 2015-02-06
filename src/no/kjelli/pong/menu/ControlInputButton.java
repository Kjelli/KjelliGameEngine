package no.kjelli.pong.menu;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Focusable;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.input.AbstractButton;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class ControlInputButton extends AbstractButton implements Focusable {
	public static final float WIDTH = 100;
	public static final float HEIGHT = 40;

	public boolean selected = false;
	public InputListener listener;
	public ControlInput parent;

	public String startText;
	public int key;

	public ControlInputButton(float x, float y, String startText, ControlInput parent) {
		super(x, y, WIDTH, HEIGHT, startText, new Color(Color.darkGray),
				new Color(Color.black), new Color(Color.gray));
		this.startText = startText;
		this.parent = parent;
		listener = new InputListener() {

			@Override
			public void keyUp(int eventKey) {
				if (selected) {
					key = eventKey;
					setText(startText + " " + Keyboard.getKeyName(key));
					parent.notifyChange(ControlInputButton.this);
					Screen.setFocusOn(null);
				}
			}

			@Override
			public void keyDown(int eventKey) {
			}
		};
		Input.register(listener);
	}

	@Override
	protected void released(int mouseButton) {
	}

	@Override
	protected void clicked(int mouseButton) {
	}

	@Override
	public void update() {

	}

	@Override
	public void draw() {
		super.draw();
		if (selected) {
			Draw.rect(x, y, 4.1f, width, height,
					new Color(Color.red).brighter());
		}
	}

	@Override
	public void setFocus(boolean focus) {
		selected = focus;
	}

	@Override
	public boolean hasFocus() {
		return selected;
	}

	@Override
	public void onLostFocus() {

	}

	@Override
	public void onFocus() {
		selected = true;
	}

	@Override
	public void destroy() {
		Input.unregister(listener);
		super.destroy();
	}


}
