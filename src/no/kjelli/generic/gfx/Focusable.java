package no.kjelli.generic.gfx;

public interface Focusable {
	void setFocus(boolean focus);
	boolean hasFocus();
	
	void onLostFocus();
	void onFocus();
}
