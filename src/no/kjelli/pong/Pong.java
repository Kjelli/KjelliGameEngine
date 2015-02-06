package no.kjelli.pong;

import java.io.IOException;

import no.kjelli.generic.Callback;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextStatic;
import no.kjelli.generic.gfx.textures.SpriteSheet;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.config.PlayerConfig;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;
import no.kjelli.pong.gameobjects.Wall;
import no.kjelli.pong.gameobjects.ai.AI;
import no.kjelli.pong.gameobjects.ai.EasyAI;
import no.kjelli.pong.gameobjects.ai.HardAI;
import no.kjelli.pong.gameobjects.ai.MediumAI;
import no.kjelli.pong.gameobjects.particles.LEDLogo;
import no.kjelli.pong.menu.ControlInput;
import no.kjelli.pong.menu.EasyButton;
import no.kjelli.pong.menu.HardButton;
import no.kjelli.pong.menu.Logo;
import no.kjelli.pong.menu.MediumButton;
import no.kjelli.pong.menu.MultiPlayerButton;
import no.kjelli.pong.menu.SinglePlayerButton;
import no.kjelli.pong.menu.SoundButton;
import no.kjelli.pong.menu.StartButton;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Pong implements Game {

	/**
	 * @author Kjell Arne Hellum
	 *
	 * 
	 */
	/** The margin from top and bottom of the screen */
	public static final float MARGIN = Sprite.CHAR_HEIGHT * 7;
	/** Lower limit, ball cannot get down past this due to a Wall object */
	public static final float LOWER_LIMIT = MARGIN;
	/** Upper limit, ball cannot get over past this due to a Wall object */
	public static final float UPPER_LIMIT = 480 - MARGIN;
	/** Footer text included in all gamescreens. */
	private static final String footerText = "Spillet ble lagd i en konkurranse på IT-Dagene, arrangert av LED";

	/** Timer variable incremented by every frame. */
	public static long ticks = 0;
	/**
	 * A PlayerConfig array consisting of two objects, each for the respective
	 * /*players. Used for controls mapping, and name
	 */
	PlayerConfig[] config = new PlayerConfig[2];
	/** Static string for displaying the difficulty-choice text */
	private static String difficultyString = "Velg vanskelighetsgrad";

	/** The spritesheets used in this game */
	public static SpriteSheet objects;
	public static SpriteSheet objects_hires;

	/** The objects used in this game */
	public static Bat bat1, bat2;
	public static Ball ball;

	/** The current gamestate */
	public static STATE state;

	/**
	 * The various states a game can be in at a given time
	 * <p>
	 * <b>INTRO</b>: Typically when you have an introductory screen with a
	 * <i>'Press any key to begin'</i> type of thing.
	 * <p>
	 * <b>MENU</b>: After passing from the intro-screen, you get to the menu
	 * where you can tweak options and do preliminary game-related actions.
	 * before starting
	 * <p>
	 * <b>PLAYING</b>: When you consider a player to be ingame.
	 * <p>
	 * <b>GAMEOVER</b>: Whenever a player has ended his/her game.
	 * 
	 */
	public static enum STATE {
		INTRO, MENU, PLAYING, GAMEOVER
	}

	/** Initializes and loads elements of the game and starting the intro */
	@Override
	public void init() {
		loadSpritesheet();
		loadSounds();
		initIntro();
	}

	/**
	 * Loads textures and initializing them as spritesheets. The gameobjects use
	 * these spritesheets for their graphics.
	 */
	private void loadSpritesheet() {
		objects = new SpriteSheet("res\\pong.png");
		objects_hires = new SpriteSheet("res\\ponghires.png",
				SpriteSheet.LINEAR);
	}

	/**
	 * Loads sound effects which are used throughout the game.
	 */
	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
			SoundPlayer.load("laser1.wav");
			SoundPlayer.load("hit1.wav");
			SoundPlayer.load("speedup.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the intro, clearing the world of any remaining objects and
	 * drawing an intro screen.
	 */
	public void initIntro() {
		World.clear();
		state = STATE.INTRO;
		initBackground();
		World.add(new Logo(Screen.getCenterX() - Logo.WIDTH / 2, Screen
				.getHeight() * 3 / 4 - Logo.HEIGHT / 2));

		World.add(new StartButton(Screen.getCenterX() - StartButton.WIDTH / 2,
				Screen.getHeight() / 4 - StartButton.HEIGHT / 2, this));

		World.add(new SoundButton(Screen.getCenterX() - StartButton.WIDTH / 2,
				Screen.getHeight() / 8 - StartButton.HEIGHT / 2));

		World.add(new TextStatic(footerText, Screen.getCenterX()
				- footerText.length() * Sprite.CHAR_WIDTH / 2,
				Sprite.CHAR_HEIGHT, Color.yellow));
	}

	/**
	 * Initializes the gamemode-choice screen. Here you can choose whether to
	 * play SinglePlayer or Multiplayer.
	 */
	public void initGameModeChoice() {
		World.clear();
		state = STATE.MENU;
		initBackground();
		World.add(new Logo(Screen.getCenterX() - Logo.WIDTH / 2, Screen
				.getHeight() * 3 / 4 - Logo.HEIGHT / 2));
		World.add(new SinglePlayerButton(Screen.getCenterX()
				- StartButton.WIDTH / 2, Screen.getHeight() * 3 / 8
				- StartButton.HEIGHT / 2, this));
		World.add(new MultiPlayerButton(Screen.getCenterX() - StartButton.WIDTH
				/ 2, Screen.getHeight() * 2 / 8 - StartButton.HEIGHT / 2, this));
		World.add(new TextStatic(footerText, Screen.getCenterX()
				- footerText.length() * Sprite.CHAR_WIDTH / 2,
				Sprite.CHAR_HEIGHT, Color.yellow));

	}

	/**
	 * Initializes the singleplayer-difficulty choice screen. In this screen you
	 * can pick between three difficulties. The difficulties affect the AI of
	 * the opponent bat.
	 */
	public void initSinglePlayerChoice() {
		World.clear();
		World.add(new TextStatic(difficultyString, Screen.getCenterX()
				- difficultyString.length() * Sprite.CHAR_WIDTH / 2, Screen
				.getHeight() * 6 / 8 - Sprite.CHAR_HEIGHT / 2, Color.white));
		World.add(new EasyButton(Screen.getCenterX() - EasyButton.WIDTH / 2,
				Screen.getHeight() * 5 / 8 - EasyButton.HEIGHT / 2, this));
		World.add(new MediumButton(Screen.getCenterX() - EasyButton.WIDTH / 2,
				Screen.getHeight() * 4 / 8 - EasyButton.HEIGHT / 2, this));
		World.add(new HardButton(Screen.getCenterX() - EasyButton.WIDTH / 2,
				Screen.getHeight() * 3 / 8 - EasyButton.HEIGHT / 2, this));
		World.add(new TextStatic(footerText, Screen.getCenterX()
				- footerText.length() * Sprite.CHAR_WIDTH / 2,
				Sprite.CHAR_HEIGHT, Color.yellow));

	}

	/**
	 * Initializes a screen for mapping controls, and player name - before
	 * starting the game with the given difficulty level
	 * 
	 * @param difficulty
	 *            - The given difficulty level the opponent bat will have.
	 *            <i>EasyAI, MediumAI</i> and <i>HardAI</i>.
	 */
	public void initSinglePlayer(int difficulty) {
		World.clear();
		config[0] = new PlayerConfig("LEFTIE", Keyboard.KEY_W, Keyboard.KEY_S,
				Keyboard.KEY_D, 0);

		bat1 = new Bat(100, Screen.getCenterY() - Bat.HEIGHT / 2, config[0]);

		AI ai = null;
		switch (difficulty) {
		case 1:
			ai = new EasyAI();
			break;
		case 2:
			ai = new MediumAI();
			break;
		case 3:
			ai = new HardAI();
			break;
		default:
			assert false;

		}

		bat2 = new Bat(Screen.getWidth() - 100, Screen.getCenterY()
				- Bat.HEIGHT / 2, ai);

		fetchControlInput(config[0], new Callback() {
			@Override
			public void run() {
				initGame();
			}
		});

	}

	/**
	 * Initializes a screen for mapping controls, and player names - before
	 * starting the game in multiplayer mode
	 * 
	 * 
	 */
	public void initMultiPlayer() {
		World.clear();
		state = STATE.MENU;
		config[0] = new PlayerConfig("LEFTIE", Keyboard.KEY_W, Keyboard.KEY_S,
				Keyboard.KEY_D, 0);
		config[1] = new PlayerConfig("RIGHTIE", Keyboard.KEY_UP,
				Keyboard.KEY_DOWN, Keyboard.KEY_LEFT, 1);

		bat1 = new Bat(100, Screen.getCenterY() - Bat.HEIGHT / 2, config[0]);

		bat2 = new Bat(Screen.getWidth() - 100, Screen.getCenterY()
				- Bat.HEIGHT / 2, config[1]);
		fetchControlInput(config[0], new Callback() {
			@Override
			public void run() {
				World.clear();
				fetchControlInput(config[1], new Callback() {
					@Override
					public void run() {
						initGame();
					}
				});
			}
		});
	}

	/**
	 * Initializes a screen for mapping controls, and player names. After the
	 * confirm-button has been clicked, a supplied callback-method will run
	 * 
	 * @param pc
	 *            - The PlayerConfig object used to store the user-mapped
	 *            controls. This object is later used as a reference when a bat
	 *            object is created
	 * @param callback
	 *            - The callback method that gets called after the user inputs a
	 *            valid input-scheme for the controls (non-duplicate keys)
	 */
	private void fetchControlInput(PlayerConfig pc, Callback callback) {
		new ControlInput(Screen.getCenterX(), Screen.getCenterY(), pc, callback);
		World.add(new TextStatic(footerText, Screen.getCenterX()
				- footerText.length() * Sprite.CHAR_WIDTH / 2,
				Sprite.CHAR_HEIGHT, Color.yellow));
	}

	/**
	 * Initializes the main game with two predefined global bats and the ball.
	 * An inputlistener is also attached for resetting the game by pressing
	 * <i>ESCAPE</i>
	 */

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		World.add(bat1);
		World.add(bat2);

		ball = new Ball(Screen.getCenterX() - Ball.WIDTH / 2,
				Screen.getCenterY() - Ball.HEIGHT / 2);

		World.add(new Wall(0, 0, Screen.getWidth(), LOWER_LIMIT));
		World.add(new Wall(0, UPPER_LIMIT, Screen.getWidth(), Screen
				.getHeight() - UPPER_LIMIT));

		World.add(ball);

		Input.register(new InputListener() {

			@Override
			public void keyDown(int eventKey) {
				if (eventKey == Keyboard.KEY_ESCAPE) {
					initIntro();
					Input.unregister(this);
				}
			}

			@Override
			public void keyUp(int eventKey) {
			}
		});
		World.add(new TextStatic(footerText, Screen.getCenterX()
				- footerText.length() * Sprite.CHAR_WIDTH / 2,
				Sprite.CHAR_HEIGHT, Color.yellow));
	}

	/**
	 * Every object will be iterated through and have its designated draw method
	 * called to draw each object individually
	 */

	@Override
	public void render() {
		Screen.render();
	}

	/**
	 * Every object will be iterated through and have its designated update
	 * method called. Each object behaves differently and requires their own
	 * defined update method accordingly. Depending on which state the game is
	 * in, different groups of the objects can be updated, or objects can be
	 * updated differently.
	 */

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case MENU:
		case INTRO:
			updateBackground();
			break;

		case PLAYING:
			break;
		case GAMEOVER:
			break;
		default:
			break;

		}

		World.update();
		Screen.update();

	}

	/**
	 * The amount of LED-logos flying around in the background in the menu
	 */
	static int numOfParticles = 80;

	/**
	 * Creates randomly placed LED-logos around the background to create a
	 * visual effect.
	 */
	private void initBackground() {
		for (int i = numOfParticles - World.getObjects().size() - 1; i >= 0; i--) {
			float x = (float) (Math.random()
					* (Screen.getWidth() + LEDLogo.WIDTH * 2) - LEDLogo.WIDTH), y = (float) (Math
					.random() * (Screen.getHeight() + LEDLogo.HEIGHT * 2) - LEDLogo.HEIGHT);
			World.add(new LEDLogo(x, y, (float) Math.random() * 0.8f + 0.2f));
		}
	}

	/**
	 * Creates randomly placed LED-logos around the background to create a
	 * visual effect. This update checks if the current total number of objects
	 * is less than <i>numOfParticles</i>, and if so, insert a new object into
	 * the background.
	 */
	private void updateBackground() {
		if (World.getObjects().size() < numOfParticles) {
			float x = (float) (Math.random()
					* (Screen.getWidth() + LEDLogo.WIDTH * 2) - LEDLogo.WIDTH), y = (float) (Math
					.random() * (Screen.getHeight() + LEDLogo.HEIGHT * 2) - LEDLogo.HEIGHT);
			World.add(new LEDLogo(x, y, (float) Math.random() * 0.8f + 0.2f));
		}
	}

	/**
	 * When a point is scored the ball and bats need to reset their respective
	 * variables, i.e. height, position, charge etc.
	 */
	public static void reset() {
		bat1.reset();
		bat2.reset();
		ball.reset();
	}

	/**
	 * When the game is closed, each object is destroyed in their own manner to
	 * ensure the closing of every resource in use.
	 */
	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}

	}

	@Override
	public double getWidth() {
		return 640;
	}

	@Override
	public double getHeight() {
		return 480;
	}

	@Override
	public String getTitle() {
		return "Pong";
	}

	public static void main(String[] args) {
		new GameWrapper(new Pong(), false);
	}

}
