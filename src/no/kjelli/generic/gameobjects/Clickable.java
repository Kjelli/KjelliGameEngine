package no.kjelli.generic.gameobjects;

import no.kjelli.generic.gfx.Drawable;

public interface Clickable extends Drawable {
	public void onMousePressed(int mouseButton);

	public void onMouseReleased(int mouseButton);

	public void onEnter();

	public void onExit();

	public boolean contains(float x, float y);
}
