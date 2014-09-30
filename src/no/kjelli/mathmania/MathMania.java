package no.kjelli.mathmania;

import static org.lwjgl.input.Keyboard.KEY_Q;
import static org.lwjgl.input.Keyboard.isKeyDown;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.levels.Input;
import no.kjelli.mathmania.levels.Level;
import no.kjelli.mathmania.levels.Question;

import org.newdawn.slick.Color;

public class MathMania implements Game {
	public static int tag_playfield = Tagger.uniqueTag();
	public static Question currentQuestion;
	public static Input input = new Input();

	public static int question_cooldown = 0;
	public static final int QUESTION_COOLDOWN = 20;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	public static enum PLAYSTATE {
		PLAYFIELD, QUESTION
	}

	public static STATE state;
	public static PLAYSTATE playstate;
	public static long ticks = 0;

	private static int cooldown = 0;
	private static Level currentLevel;

	@Override
	public void init() {
		loadSounds();
		initIntro();
		initGame();
	}

	public void initIntro() {
		World.clear();
		state = STATE.INTRO;
	}

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		Screen.setBackgroundColor(Color.black);

		currentLevel = new Level("1");
		currentLevel.start();
	}

	public static void initQuestion(Question q) {
		if (question_cooldown > 0)
			return;

		playstate = PLAYSTATE.QUESTION;

		World.pause(tag_playfield, true);
		World.hide(tag_playfield, true);

		input = new Input(Screen.getCenterX(), Screen.getCenterY(), q);
		World.add(input);

		currentQuestion = q;
		World.add(q);

		question_cooldown = QUESTION_COOLDOWN;
	}

	public static void resumeGameplay() {
		if (question_cooldown > 0)
			return;
		playstate = PLAYSTATE.PLAYFIELD;
		World.pause(tag_playfield, false);
		World.hide(tag_playfield, false);

		input.destroy();
		currentQuestion.destroy();

		question_cooldown = QUESTION_COOLDOWN;
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("res\\bounce.wav");
			SoundPlayer.load("res\\win.wav");
			SoundPlayer.load("res\\lose.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		Screen.render();
	}

	@Override
	public void getInput() {
		switch (state) {
		case INTRO:
			break;
		case LOADING:
			break;
		case MENU:
			break;
		case PLAYING:
			if (isKeyDown(KEY_Q))
				Screen.toggleDebugDraw();
			break;
		default:
			break;

		}
	}

	@Override
	public void update() {
		ticks++;
		if (question_cooldown > 0)
			question_cooldown--;
		switch (state) {
		case INTRO:
			break;
		case LOADING:
			break;
		case MENU:
			break;
		case PLAYING:
			break;
		default:
			break;

		}

		World.update();
		Screen.update();

		if (cooldown > 0)
			cooldown--;

	}

	public static void main(String[] args) {
		new Main(new MathMania(), "MathMania", 1366, 768, true);
	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}
	}

}
