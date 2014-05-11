package no.kjelli.generic;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import no.kjelli.generic.gameobjects.GameObject;

import org.newdawn.slick.Color;

public class Screen {

	private static int x;
	private static int y;
	private static int width;
	private static int height;
	private static Color backgroundColor;

	private static int shakeTimer;
	private static int shakeMagnitude;
	private static int offsetX;
	private static int offsetY;
	private static float alpha;

	public static void init(int x, int y, int width, int height) {
		init(x, y, width, height, Color.black);
	}

	public static void init(int x, int y, int width, int height,
			Color backgroundColor) {
		Screen.x = x;
		Screen.y = y;
		Screen.width = width;
		Screen.height = height;
		setBackgroundColor(backgroundColor);
		setTransparency(1.0f);
	}

	public static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		World.render();
		Draw.rect(x + 1, y, width - 1, height - 1, Color.white);
		// Physics.quadtree.render();
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

	public static int getX() {
		return x + offsetX;
	}

	public static void setX(int x) {
		Screen.x = x;
	}

	public static int getY() {
		return y + offsetY;
	}

	public static void setY(int y) {
		Screen.y = y;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Screen.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Screen.height = height;
	}

	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	public static void setBackgroundColor(Color backgroundColor) {
		glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b,
				backgroundColor.a);
	}

	public static void setTransparency(float transparency) {
		if (transparency < 0 || transparency > 1) {
			System.err.println("Transparency must be between 0.0f and 1.0f!");
			return;
		}
		alpha = transparency;
	}

	public static float getTransparency() {
		return alpha;
	}

	public static void shake(int duration, int magnitude) {
		if (duration < 0) {
			System.err.println("Duration can't be less than 0 !");
			return;
		}

		shakeTimer = duration;
		shakeMagnitude = magnitude;
	}

	public static void update() {
		if (shakeTimer > 0) {
			offsetX = (int) (2 * Math.random() * shakeMagnitude - shakeMagnitude);
			offsetY = (int) (2 * Math.random() * shakeMagnitude - shakeMagnitude);
			shakeTimer--;
		} else if (offsetX != 0 || offsetY != 0) {
			offsetX = 0;
			offsetY = 0;
		}
	}
}
