package no.kjelli.generic.main;

import static org.lwjgl.opengl.GL11.*;
import no.kjelli.generic.Game;
import no.kjelli.generic.Physics;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

	private static final int FRAME_RATE = 60;
	private static Game game;

	public Main(Game game, String title, int width, int height,
			boolean fullscreen) {
		Main.game = game;
		initDisplay(width, height, fullscreen);
		Display.setTitle(title);
		initInput();
		initGL();

		initGame();

		gameLoop();
		cleanUp();
	}

	private static void initInput() {
		try {
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private static void initDisplay(int width, int height, boolean fullscreen) {
		try {
			setDisplayMode(width, height, fullscreen);
			Display.setVSyncEnabled(true);
			Display.create();

		} catch (LWJGLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Set the display mode to be used
	 * 
	 * @param width
	 *            The width of the display required
	 * @param height
	 *            The height of the display required
	 * @param fullscreen
	 *            True if we want fullscreen mode
	 */
	public static void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width)
				&& (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width)
							&& (current.getHeight() == height)) {
						if ((targetDisplayMode == null)
								|| (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode
											.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display
										.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x"
						+ height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height
					+ " fullscreen=" + fullscreen + e);
		}
	}

	private static void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_ADD);
		glLoadIdentity();

	}

	private static void initGame() {
		Screen.init(0, 0, Display.getWidth(), Display.getHeight());
		World.init(Display.getWidth(), Display.getHeight());
		Physics.init();
		game.init();
	}

	private static void getInput() {
		game.getInput();

	}

	private static void gameLoop() {
		while (!Display.isCloseRequested()) {
			getInput();
			update();
			render();
			calculateFrameRate();
		}
	}

	static long lastTime = System.nanoTime();
	static long incrementer;
	static long frameCounter;
	public static long framesPerSecond;

	private static void calculateFrameRate() {
		long now = System.nanoTime();
		double diff = now - lastTime;
		lastTime = now;
		incrementer += diff / 1000000L;
		frameCounter++;
		if (incrementer > 1000) {
			incrementer -= 1000;
			framesPerSecond = frameCounter;
			frameCounter = 0;
		}
	}

	private static void update() {
		SoundPlayer.update();
		game.update();
	}

	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		game.render();
		
		glFlush();

		Display.update();
		Display.sync(FRAME_RATE);
	}

	private static void cleanUp() {
		game.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		Display.destroy();
		AL.destroy();
	}

}
