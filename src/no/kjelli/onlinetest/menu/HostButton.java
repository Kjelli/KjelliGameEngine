package no.kjelli.onlinetest.menu;

import org.newdawn.slick.Color;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.onlinetest.OnlineTest;

public class HostButton extends Button {
	public static final int width = 100, height = 50;

	public HostButton(float x, float y) {
		super(x, y, width, height, "Host");
		setHighlight_color(new Color(Color.gray));
		setIdle_color(new Color(Color.black));
		setClick_color(new Color(Color.darkGray));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, 1f, width, height, color);
		Draw.rect(x, y, 2f, width, height, 0, Color.white, false);
		Draw.string(text,
				x + width / 2 - text.length() * Sprite.CHAR_WIDTH / 2, y
						+ height / 2 - Sprite.CHAR_HEIGHT / 2, 3f, 1f, 1f,
				Color.white, false);
	}

	@Override
	protected void released(int mouseButton) {
		OnlineTest.initGame(true);
	}

	@Override
	protected void clicked(int mouseButton) {
	}

}
