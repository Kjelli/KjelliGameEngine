package no.kjelli.generic.gfx;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glScalef;

import java.util.HashSet;
import java.util.LinkedHashSet;

import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Screen {
	private static final int DEBUG_DRAW_MODES = 3; // off, objects, objects +
													// quad

	public static final int MOUSE_LEFT = 0, MOUSE_RIGHT = 1;

	private static float x;
	private static float y;
	private static int width;
	private static int height;
	private static Color backgroundColor;

	private static int shakeTimer;
	private static int shakeMagnitude;
	private static int offsetX;
	private static int offsetY;
	private static float alpha;

	private static float velocity_x, velocity_y;
	private static GameObject centerTarget;

	private static Focusable focus = null;

	public static float scale = 1.0f;

	private static Cursor blankCursor;
	private static HashSet<Clickable> mouseOverEventObjects;
	private static HashSet<Clickable> mouseOverEventObjectsRemoveQueue;
	private static float damping;

	private static int debug_draw_mode = 0;


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
		mouseOverEventObjectsRemoveQueue = new HashSet<>();

		try {
			blankCursor = new Cursor(1, 1, 0, 0, 1,
					BufferUtils.createIntBuffer(1), null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Input.register(new InputListener() {

			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_Q) {
					Screen.toggleDebugDraw();
				}
			}

			@Override
			public void keyDown(int eventKey) {
				
			}
		});
	}

	public static void zoom(float scale) {
		Screen.scale = 1 / scale;
	}

	public static void render() {
		glScalef(1 / scale, 1 / scale, 1f);
		World.render();
		if (debug_draw_mode > 0) {
			Draw.string("FPS: " + GameWrapper.framesPerSecond + "\nObjects: "
					+ World.getObjects().size(), 1, Screen.getHeight()
					- Sprite.CHAR_HEIGHT - 1, 4.2f, 1, 1, Color.yellow, true);
		}
		if (debug_draw_mode > 1)
			Physics.quadtree.render();
	}

	public static void toggleDebugDraw() {
		debug_draw_mode = (debug_draw_mode + 1) % DEBUG_DRAW_MODES;
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
		if (centerTarget != null)
			followCenterTarget();

		Mouse.poll();
		while (Mouse.next()) {
			for (Clickable c : mouseOverEventObjectsRemoveQueue) {
				mouseOverEventObjects.remove(c);
			}
			mouseOverEventObjectsRemoveQueue.clear();
			releaseMouseOverObjects();
			checkWorldMouseEvents();
		}

	}

	private static void followCenterTarget() {
		velocity_x = (centerTarget.getCenterX() - getCenterX()) / damping;
		velocity_y = (centerTarget.getCenterY() - getCenterY()) / damping;
		setX((getX() + velocity_x));
		setY((getY() + velocity_y));
	}

	static boolean foundFocusable = false, clicked = false;

	private static void checkWorldMouseEvents() {
		LinkedHashSet<GameObject> returnObjects = new LinkedHashSet<>();
		World.retrieveAll(returnObjects, new Rectangle(getMouseX(),
				getMouseY(), 1, 1));
		foundFocusable = false;
		clicked = false;
		for (GameObject obj : returnObjects) {
			if (obj instanceof Clickable) {
				Clickable src = (Clickable) obj;
				doMouseEvents(src);
				break;
			}
		}
		if (Mouse.getEventButtonState()) {
			clicked = true;
		}
		if (clicked && !foundFocusable) {
			setFocusOn(null);
		}
	}

	private static void doMouseEvents(Clickable src) {
		if (Mouse.getEventButton() != -1) {
			int button = Mouse.getEventButton();
			if (Mouse.getEventButtonState()) {
				src.onMousePressed(button);
				if (src instanceof Focusable) {
					setFocusOn(((Focusable) src), true);
					foundFocusable = true;
				}
			} else {
				src.onMouseReleased(button);
				foundFocusable = true;
			}
			return;
		}

		if (!mouseOverEventObjects.contains(src)) {
			mouseOverEventObjects.add(src);
			src.onEnter();

		}
	}

	public static void centerOn(GameObject object) {
		centerOn(object, 10);
	}

	public static void centerOn(GameObject object, int damping) {
		centerTarget = object;
		Screen.damping = damping;
	}

	public static boolean contains(GameObject object) {
		float x = object.getX();
		float y = object.getY();
		float width = object.getWidth();
		float height = object.getHeight();

		boolean inXBounds = (x < getWidth() && x + width > 0);
		boolean inYBounds = (y < getHeight() && y + height > 0);

		return inXBounds && inYBounds;
	}

	public static float getX() {
		return x + offsetX;
	}

	public static void setX(float x) {
		Screen.x = x;
	}

	public static float getY() {
		return y + offsetY;
	}

	public static void setY(float y) {
		Screen.y = y;
	}

	public static int getWidth() {
		return (int) (width * scale);
	}

	public static void setWidth(int width) {
		Screen.width = width;
	}

	public static int getHeight() {
		return (int) (height * scale);
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
			if (!(c.contains(getMouseX(), getMouseY()))) {
				mouseOverEventObjectsRemoveQueue.add(c);
				c.onExit();
			}
		}
	}

	private static int getMouseX() {
		return (int) (Mouse.getX() * ((float) Screen.getWidth() / Display
				.getWidth()));
	}

	private static int getMouseY() {
		return (int) (Mouse.getY() * ((float) Screen.getHeight() / Display
				.getHeight()));
	}

	public static float getCenterX() {
		return getX() + getWidth() / 2;
	}

	public static float getCenterY() {
		return getY() + getHeight() / 2;
	}

	public static void setFocusOn(Focusable focusable) {
		setFocusOn(focusable, true);
	}

	public static void setFocusOn(Focusable focusable, boolean focus) {
		if (Screen.focus == focusable)
			return;
		if (Screen.focus != null) {
			Screen.focus.setFocus(false);
			Screen.focus.onLostFocus();
			if (focusable == null) {
				Screen.focus = null;
				return;
			}
		} else {
			if (focusable == null)
				return;
		}
		if (focus) {
			Screen.focus = focusable;
			Screen.focus.setFocus(true);
			Screen.focus.onFocus();
		} else
			Screen.focus = null;
	}

	public static Focusable getFocusElement() {
		return focus;
	}
	
	public static void dispose(){
		//TODO
	}
	public static void incrementX(int x) {
		Screen.setX(Screen.x + x);
	}
	public static void incrementY(int y) {
		Screen.setY(Screen.y + y);
	}
}
