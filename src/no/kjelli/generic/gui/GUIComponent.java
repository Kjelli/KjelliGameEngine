package no.kjelli.generic.gui;

import no.kjelli.generic.gfx.Drawable;

public interface GUIComponent extends Drawable {

	public boolean contains(float x, float y);

	public void update();

}
