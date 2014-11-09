package no.kjelli.onlinetest.menu;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;

import org.newdawn.slick.Color;

public abstract class Button extends AbstractGameObject implements Clickable{
	public String text;
	
	protected Color color;
	private Color highlight_color;
	private Color idle_color;
	private Color click_color;
	

	public Button(float x, float y, float width, float height, String text) {
		super(x, y, width, height);
		this.text = text;
	}

	@Override
	public void onCreate() {
		setVisible(true);
		color = idle_color;
	}
	

	@Override
	public void onMousePressed(int mouseButton) {
		color = click_color;
		clicked(mouseButton);
	}
	
	@Override
	public void onMouseReleased(int mouseButton) {
		color = highlight_color;
		released(mouseButton);
	}
	
	protected abstract void released(int mouseButton);
	protected abstract void clicked(int mouseButton);
	
	@Override
	public void onEnter() {
		color = highlight_color;
	}

	@Override
	public void onExit() {
		color = idle_color;
	}
	public Color getHighlight_color() {
		return highlight_color;
	}
	
	public void setHighlight_color(Color highlight_color) {
		this.highlight_color = highlight_color;
	}
	
	public Color getIdle_color() {
		return idle_color;
	}
	
	public void setIdle_color(Color idle_color) {
		this.idle_color = idle_color;
	}
	
	public Color getClick_color() {
		return click_color;
	}
	
	public void setClick_color(Color click_color) {
		this.click_color = click_color;
	}
}
