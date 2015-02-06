package no.kjelli.generic.gfx;

public interface Focusable {
	void setFocus(boolean focus);
	
	void onLostFocus();
	void onFocus();

	boolean hasFocus();
}
