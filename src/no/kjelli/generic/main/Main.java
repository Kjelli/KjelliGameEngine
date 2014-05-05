package no.kjelli.generic.main;

import static org.lwjgl.opengl.GL11.*;
import no.kjelli.generic.Game;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

	private static Game game;

	public Main(Game game) {
		Main.game = game;

		initDisplay();
		initGL();

		initGame();

		gameLoop();
		cleanUp();
	}

	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setVSyncEnabled(true);
			Display.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0, 0, 0, 1);

		glDisable(GL_DEPTH_TEST);
	}

	private static void initGame() {
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
	}

}
