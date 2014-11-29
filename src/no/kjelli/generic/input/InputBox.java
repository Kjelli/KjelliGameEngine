package no.kjelli.generic.input;

import no.kjelli.generic.gfx.AbstractUIObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Focusable;
import no.kjelli.generic.gfx.Sprite;
import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public abstract class InputBox extends AbstractUIObject implements Focusable {
	private String input;
	private int maxLength = 8;
	private int ticks = 0;

	public InputBox(float x, float y, int maxLength) {
		super(x, y, 4f, Sprite.CHAR_WIDTH * maxLength + 2,
				Sprite.CHAR_HEIGHT + 2);
		input = "";
		this.maxLength = maxLength;
		Keyboard.enableRepeatEvents(true);
		Input.register(new InputListener() {

			@Override
			public void keyUp(int eventKey) {
			}

			@Override
			public void keyDown(int eventKey) {
				if (hasFocus())
					keyPress(eventKey);
			}
		});
	}

	protected void keyPress(int eventKey) {
		String s = getKeyName(eventKey);
		if (s.length() < 2 && input.length() < maxLength) {
			input += s;
		} else {
			switch (eventKey) {
			case KEY_PERIOD:
				if (input.length() >= maxLength)
					return;

				if (Input.shift())
					input += ":";
				else
					input += ".";
				break;
			case KEY_SPACE:
				if (input.length() >= maxLength)
					return;

				input += " ";
				break;
			case KEY_MINUS:
				if (input.length() >= maxLength)
					return;

				input += "-";
				break;

			default:
				break;

			case KEY_BACK:
				if (input.length() > 0)
					input = input.substring(0, input.length() - 1);
				break;
			case KEY_RETURN:
				onInputComplete();
				break;
			case KEY_ESCAPE:
				clear();
				break;
			}
		}
	}

	protected void clear() {
		input = "";
	}

	public String getText() {
		return input;
	}

	protected abstract void onInputComplete();

	@Override
	public void update() {
		ticks++;
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, width, height, Color.black);
		Draw.rect(x, y, 4f, width, height, Color.white);

		Draw.string(getDrawString(), (x + width / 2 - Sprite.CHAR_WIDTH / 2)
				- (input.length() < maxLength ? input.length()
						: input.length() - 1) * Sprite.CHAR_WIDTH / 2, (y
				+ height / 2 - Sprite.CHAR_HEIGHT / 2) + 1, 2.0f, 1.0f, 1.0f,
				Color.white, true);
	}

	private String getDrawString() {
		return input
				+ (((ticks % 25) > 12 && input.length() < maxLength && hasFocus()) ? "_"
						: "");
	}
}
