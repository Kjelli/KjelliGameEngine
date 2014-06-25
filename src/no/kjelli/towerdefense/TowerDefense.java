package no.kjelli.towerdefense;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.towerdefense.map.Map;

import org.lwjgl.input.Keyboard;

public class TowerDefense implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static STATE state;
	public static long ticks = 0;

	private int cooldown = 0;

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

		Map map = Map.build(14, 10);
		map.setX(40);
		map.setY(40);
		map.setVisible(true);
		World.add(map);
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
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
				Screen.toggleDebugDraw();
			break;
		default:
			break;

		}
	}

	@Override
	public void update() {
		ticks++;

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
		new Main(new TowerDefense(), "Tower Defense", 640, 480, false);
	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}
	}

}
