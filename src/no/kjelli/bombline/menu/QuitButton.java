package no.kjelli.bombline.menu;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;

public class QuitButton extends Button {
	public static final int width = 100, height = 50;
	
	public QuitButton(float x, float y) {
		super(x, y, width, height, "Quit");
		setHighlight_color(new Color(Color.gray));
		setIdle_color(new Color(Color.black));
		setClick_color(new Color(Color.darkGray));
	}

	@Override
	public void update() {
	}

	@Override
	protected void released(int mouseButton) {
		BombermanOnline.requestClose();
	}

	@Override
	protected void clicked(int mouseButton) {
	}

}
