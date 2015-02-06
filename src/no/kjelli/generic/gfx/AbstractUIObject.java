package no.kjelli.generic.gfx;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class AbstractUIObject extends AbstractGameObject implements Focusable, Clickable{
	private boolean focus = false;
	public AbstractUIObject(float x, float y, float z, float width, float height) {
		super(x, y, z, width, height);
	}

	@Override
	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	@Override
	public boolean hasFocus() {
		return focus;
	}

}
