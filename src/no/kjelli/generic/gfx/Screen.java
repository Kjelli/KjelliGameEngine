package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gui.GUIComponent;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Screen {

	public static final int MOUSE_LEFT = 0, MOUSE_RIGHT = 1;

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

	private static Cursor blankCursor;
	private static HashSet<Clickable> mouseOverEventObjects;
	private static ArrayList<GUIComponent> guiComponents;
	private static ArrayList<GUIComponent> addQueue;
	private static ArrayList<GUIComponent> removeQueue;

	private static int debug_cooldown;
	private static final int DEBUG_COOLDOWN_MAX = 30;
	private static boolean debug_draw = false;

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

		mouseOverEventObjects = new HashSet<>();
		guiComponents = new ArrayList<>();
		addQueue = new ArrayList<>();
		removeQueue = new ArrayList<>();

		try {
			blankCursor = new Cursor(1, 1, 0, 0, 1,
					BufferUtils.createIntBuffer(1), null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		World.render();
		renderGUI();
		if (debug_draw) {
			Draw.rect(x, y, width - 1, height - 1, Color.white);
			Physics.quadtree.render();
		}
	}

	public static void toggleDebugDraw() {
		if (debug_cooldown == 0) {
			debug_draw = !debug_draw;
			debug_cooldown = DEBUG_COOLDOWN_MAX;
		}
	}

	public static void update() {
		if (debug_cooldown > 0)
			debug_cooldown--;
		if (shakeTimer > 0) {
			offsetX = (int) (2 * Math.random() * shakeMagnitude - shakeMagnitude);
			offsetY = (int) (2 * Math.random() * shakeMagnitude - shakeMagnitude);
			shakeTimer--;
		} else if (offsetX != 0 || offsetY != 0) {
			offsetX = 0;
			offsetY = 0;
		}

		for (GUIComponent g : addQueue) {
			guiComponents.add(g);
		}
		for (GUIComponent g : removeQueue) {
			guiComponents.remove(g);
		}

		addQueue.clear();
		removeQueue.clear();

		for (GUIComponent g : guiComponents)
			g.update();

		Mouse.poll();
		while (Mouse.next()) {
			releaseMouseOverObjects();
			checkGUIMouseEvents();
			checkWorldMouseEvents();
		}

	}

	private static void checkGUIMouseEvents() {
		for (GUIComponent g : guiComponents) {
			if (!(g instanceof Clickable))
				continue;

			if (g.contains(Mouse.getX(), Mouse.getY())) {
				Clickable src = (Clickable) g;
				doMouseEvents(src);

			}
		}
	}

	private static void checkWorldMouseEvents() {
		HashSet<GameObject> returnObjects = new HashSet<>();
		World.retrieveAll(returnObjects,
				new Rectangle(Mouse.getX() - 1, Mouse.getY() - 1, 2, 2));
		for (GameObject obj : returnObjects) {
			if (obj instanceof Clickable) {
				Clickable src = (Clickable) obj;
				doMouseEvents(src);
			}
		}
	}

	private static void doMouseEvents(Clickable src) {
		if (Mouse.getEventButton() != -1) {
			int button = Mouse.getEventButton();
			if (Mouse.getEventButtonState())
				src.onMousePressed(button);
			else
				src.onMouseReleased(button);
		}

		if (!mouseOverEventObjects.contains(src)) {
			mouseOverEventObjects.add(src);
			src.onEnter();

		}
	}

	public static void add(GUIComponent g) {
		addQueue.add(g);
	}

	public static void remove(GUIComponent g) {
		removeQueue.add(g);
	}

	public static void clearGUI() {
		removeQueue.addAll(guiComponents);
	}

	private static void renderGUI() {
		for (Drawable d : guiComponents)
			d.draw();
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

	public static void showCursor(boolean visible) {
		if (Mouse.isInsideWindow())
			try {
				if (visible)
					Mouse.setNativeCursor(null);
				else
					Mouse.setNativeCursor(blankCursor);
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
	}

	public static boolean isCursorVisible() {
		return (Mouse.getNativeCursor() == null);
	}

	public static boolean hasFocus() {
		return Display.isActive();
	}

	public static void releaseMouseOverObjects() {
		for (Clickable c : mouseOverEventObjects) {
			if (!(c.contains(Mouse.getX(), Mouse.getY()))) {
				mouseOverEventObjects.remove(c);
				c.onExit();
			}
		}
	}
}
