package no.kjelli.generic.main;

import static org.lwjgl.opengl.GL11.*;
import no.kjelli.generic.*;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

	private static Game game;

	public Main(Game game) {
		Main.game = game;
		initDisplay();
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

	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);

		glDisable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

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

		Display.update();
		Display.sync(60);
	}

	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		AL.destroy();
	}

}
