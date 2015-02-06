package no.kjelli.generic.gamewrapper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MULT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glViewport;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.textures.SpriteSheet;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.generic.tweens.GameObjectAccessor;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class GameWrapper {

	private static final int FRAME_RATE = 60;
	private static Game game;
	public static Thread gameThread;
	public static TweenManager tweenManager;

	String title;
	int width, height;
	boolean fullscreen;

	boolean running = false;

	public GameWrapper(Game game, boolean fullscreen) {
		GameWrapper.game = game;
		this.title = game.getTitle();
		this.width = (int) game.getWidth();
		this.height = (int) game.getHeight();
		this.fullscreen = fullscreen;
		launch();
	}

	public void stop() {
		running = false;
	}

	public void launch() {
		running = true;
		
		Tween.registerAccessor(AbstractGameObject.class, new GameObjectAccessor());
		initTweenEngine();
		
		
		gameThread = new Thread() {
			public void run() {

				initDisplay(width, height, fullscreen);
				Display.setTitle(title);
				initInput();
				initGL();
				initGame();
				gameLoop();
				cleanUp();
			}
		};
		gameThread.start();
	}

	private void initInput() {
		try {
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private void initDisplay(int width, int height, boolean fullscreen) {
		try {
			setDisplayMode(width, height, fullscreen);
			Display.setVSyncEnabled(true);

			Display.setResizable(true);
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
	public void setDisplayMode(int width, int height, boolean fullscreen) {

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

	private void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -5, 5);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MULT);
		glLoadIdentity();

		
	}

	private void initGame() {
		Screen.init(0, 0, Display.getWidth(), Display.getHeight());
		World.init(Display.getWidth(), Display.getHeight());
		game.init();
		tweenManager.resume();
	}

	private void initTweenEngine() {
		tweenManager = new TweenManager();
		tweenManager.pause();
	}

	private void pollInput() {
		Input.pollInput();
	}

	private void gameLoop() {
		while (running) {
			if (Display.wasResized()) {
				glViewport(0, 0, Display.getWidth(), Display.getHeight());
			}
			if (Display.isCloseRequested()) {
				running = false;
			}
			pollInput();
			update();
			render();
			tweenManager.update(calculateFrameRate());
		}
	}

	long lastTime = System.nanoTime();
	long incrementer;
	long frameCounter;
	public static long framesPerSecond;

	public float calculateFrameRate() {
		long now = System.nanoTime();
		float diff = (now - lastTime) / 1000000L;
		lastTime = now;
		incrementer += diff;
		frameCounter++;
		if (incrementer > 1000) {
			incrementer -= 1000;
			framesPerSecond = frameCounter;
			frameCounter = 0;
		}
		
		return diff;
	}

	private void update() {
		SoundPlayer.update();
		game.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glAlphaFunc(GL_GREATER, 0);
		glLoadIdentity();

		game.render();

		glFlush();

		Display.update();
		Display.sync(FRAME_RATE);
	}

	private void cleanUp() {
		game.destroy();
		Screen.dispose();
		SpriteSheet.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		AL.destroy();
		Display.destroy();
	}

	public Game getGame() {
		return game;
	}

}
