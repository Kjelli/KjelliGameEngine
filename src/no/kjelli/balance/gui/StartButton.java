package no.kjelli.balance.gui;

import no.kjelli.balance.Balance;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gfx.Textures;
import no.kjelli.generic.gui.AbstractComponent;

import org.lwjgl.input.Keyboard;

public class StartButton extends AbstractComponent implements Clickable {
	public static final int WIDTH = 128, HEIGHT = 32;

	public StartButton(float x, float y) {
		this.x = x;
		this.y = y;
		width = WIDTH;
		height = HEIGHT;
		texture_idle = Textures.load("res\\buttonstart_idle.jpg");
		texture_pressed = Textures.load("res\\buttonstart_pressed.jpg");
		texture_highlighted = Textures.load("res\\buttonstart_highlighted.jpg");
		texture = texture_idle;
	}

	@Override
	public void onMousePressed(int mouseButton) {
		texture = texture_pressed;
	}

	@Override
	public void onMouseReleased(int mouseButton) {
		start();
	}

	@Override
	public void onEnter() {
		texture = texture_highlighted;
	}

	@Override
	public void onExit() {
		texture = texture_idle;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			start();
	}

	private void start() {
		Balance.initGame();
	}

}
