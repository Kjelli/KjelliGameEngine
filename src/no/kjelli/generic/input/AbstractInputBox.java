package no.kjelli.generic.input;

import no.kjelli.generic.gfx.AbstractUIObject;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Focusable;
import no.kjelli.generic.gfx.Sprite;
import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public abstract class AbstractInputBox extends AbstractUIObject implements Focusable,
		Clickable {
	private String input;
	private int maxLength = 8;
	private int ticks = 0;
	private Color foreground, background, bghighlight, fghighlight;
	boolean highlighted = false;
	boolean initialEdit = true;
	String defaultText;

	private boolean symbolsAllowed = true, numbersAllowed = true,
			alphanumericAllowed = true, spaceAllowed = true;

	public AbstractInputBox(float x, float y, int maxLength, String defaultText, Color foreground,
			Color background, Color bghighlight, Color fghighlight) {
		super(x, y, 4f, Sprite.CHAR_WIDTH * maxLength + 2,
				Sprite.CHAR_HEIGHT + 2);
		this.defaultText = defaultText;
		this.input = defaultText;
		this.foreground = foreground;
		this.background = background;
		this.fghighlight = fghighlight;
		this.bghighlight = bghighlight;
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
			try {
				Integer.parseInt(s);
				if (!numbersAllowed)
					return;
			} catch (Exception e) {
				if (!alphanumericAllowed)
					return;
			}
			input += s;
		} else {
			switch (eventKey) {
			case KEY_PERIOD:
				if (input.length() >= maxLength || !symbolsAllowed)
					return;

				if (Input.shift())
					input += ":";
				else
					input += ".";
				break;
			case KEY_SPACE:
				if (input.length() >= maxLength || !spaceAllowed)
					return;

				input += " ";
				break;
			case KEY_MINUS:
				if (input.length() >= maxLength || !symbolsAllowed)
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

	public void onLostFocus() {
		if (input.isEmpty() && !defaultText.isEmpty()) {
			input = defaultText;
			initialEdit = true;
		}
	}

	public void onFocus() {

		if (initialEdit && input.equals(defaultText)) {
			input = "";
			initialEdit = false;
		}
	}


	protected abstract void onInputComplete();

	@Override
	public void update() {
		ticks++;
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, width, height, highlighted ? bghighlight
				: background);
		Draw.rect(x, y, 4f, width, height, highlighted ? fghighlight
				: foreground);

		Draw.string(getDrawString(),
				(x + width / 2 - Sprite.CHAR_WIDTH / 2)
						- (input.length() < maxLength ? input.length()
								* Sprite.CHAR_WIDTH / 2 : (input.length() - 1)
								* Sprite.CHAR_WIDTH / 2)
						+ ((hasFocus() || input.length() == maxLength) ? 0
								: Sprite.CHAR_WIDTH / 2 - 1),
				(y + height / 2 - Sprite.CHAR_HEIGHT / 2) + 1, 2.0f, 1.0f,
				1.0f, highlighted ? fghighlight : foreground, true);
	}

	private String getDrawString() {
		return input
				+ (((ticks % 25) > 12 && input.length() < maxLength && hasFocus()) ? "_"
						: "");
	}

	@Override
	public void onMousePressed(int mouseButton) {
	}

	@Override
	public void onMouseReleased(int mouseButton) {
	}

	@Override
	public void onEnter() {
		highlighted = true;
	}

	@Override
	public void onExit() {
		highlighted = false;
	}

	public boolean isSymbolsAllowed() {
		return symbolsAllowed;
	}

	public void setSymbolsAllowed(boolean symbolsAllowed) {
		this.symbolsAllowed = symbolsAllowed;
	}

	public boolean isNumbersAllowed() {
		return numbersAllowed;
	}

	public void setNumbersAllowed(boolean numbersAllowed) {
		this.numbersAllowed = numbersAllowed;
	}

	public String getDefaultText(){
		return defaultText;
	}

	public boolean isAlphanumericAllowed() {
		return alphanumericAllowed;
	}

	public void setAlphanumericAllowed(boolean alphanumericAllowed) {
		this.alphanumericAllowed = alphanumericAllowed;
	}

	public boolean isSpaceAllowed() {
		return spaceAllowed;
	}

	public void setSpaceAllowed(boolean spaceAllowed) {
		this.spaceAllowed = spaceAllowed;
	}
}
