package no.kjelli.generic;

import no.kjelli.generic.gameobjects.GameObject;

public class Screen {

	public static int x;
	public static int y;
	public static int width;
	public static int height;

	public Screen(int width, int height) {
		Screen.width = width;
		Screen.height = height;
	}

	public void render() {
		World.render();
	}

	public static boolean contains(GameObject object) {
		float x = object.getX();
		float y = object.getY();
		float width = object.getWidth();
		float height = object.getHeight();

		boolean inXBounds = (x < Screen.width && x + width > 0);
		boolean inYBounds = (y < Screen.height && y + height > 0);

		return inXBounds && inYBounds;
	}
}
