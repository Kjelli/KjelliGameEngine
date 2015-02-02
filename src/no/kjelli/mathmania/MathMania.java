package no.kjelli.mathmania;

import static org.lwjgl.input.Keyboard.*;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Launcher;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.mathmania.gameobjects.Combo;
import no.kjelli.mathmania.gameobjects.Input;
import no.kjelli.mathmania.gameobjects.Question;
import no.kjelli.mathmania.gameobjects.Score;
import no.kjelli.mathmania.gameobjects.Timer;
import no.kjelli.mathmania.levels.Level;

// testtestestett

import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;

public class MathMania implements Game {
	public static int tag_playfield = Tagger.uniqueTag();
	public static Question currentQuestion;
	public static Input input;
	public static Timer timer;

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

	public Combo combo;
	private Score score;

	@Override
	public void init() {
		loadSounds();
		initIntro();
		initGame();
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
			SoundPlayer.load("win.wav");
			SoundPlayer.load("coin.wav");
			SoundPlayer.load("combo.wav");
			SoundPlayer.load("wrong.wav");
			SoundPlayer.load("right.wav");
			SoundPlayer.load("lose.wav");
			SoundPlayer.load("music.wav");
			SoundPlayer.load("comboexpire.wav");
			SoundPlayer.load("musicadd.wav");
			SoundPlayer.load("musicbeat.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initIntro() {
		World.clear();
		state = STATE.INTRO;
	}

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		Screen.setBackgroundColor(Color.black);
		Screen.zoom(2.0f);

		combo = new Combo();
		Combo.clearCombo();
		World.add(combo);

		Score.clearScore();

		timer = new Timer();
		World.add(timer);

		score = new Score();
		World.add(score);

		timer.start();

		Level.init("test");
		Level.start();

		SoundPlayer.music("music", 1.0f, 1.0f);
		SoundPlayer.play("musicadd", 1.0f, 0.0f, true);
		SoundPlayer.play("musicbeat", 1.0f, 0.0f, true);

		Screen.centerOn(Level.getPlayer());
	}

	public static void initQuestion(Question q) {
		if (question_cooldown > 0)
			return;

		playstate = PLAYSTATE.QUESTION;

		World.pause(tag_playfield, true);
		// World.hide(tag_playfield, true);

		input = new Input(q);
		World.add(input);

		currentQuestion = q;
		World.add(q);

		question_cooldown = QUESTION_COOLDOWN;
	}

	public static void resumeGameplay() {
		playstate = PLAYSTATE.PLAYFIELD;
		World.pause(tag_playfield, false);
		// World.hide(tag_playfield, false);

		currentQuestion.destroy();
		input.destroy();

		question_cooldown = QUESTION_COOLDOWN;
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
			if (isKeyDown(KEY_R)) {
				destroy();
				initGame();
			}
			if (isKeyDown(KEY_U))
				SoundPlayer.setGain("music", 1.0f);
			if (isKeyDown(KEY_J))
				SoundPlayer.setGain("music", 0.1f);
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
		new Launcher(new MathMania(), "MathMania", 800, 600, true);
	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}
	}
}
