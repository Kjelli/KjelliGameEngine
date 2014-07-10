package no.kjelli.towerdefense;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.*;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.towerdefense.gameobjects.towers.ArrowTower;
import no.kjelli.towerdefense.map.Map;

import org.lwjgl.input.Keyboard;

public class TowerDefense implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static STATE state;
	public static long ticks = 0;

	Map map;

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
	
	static ArrowTower at;
	static ArrowTower st;

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		// map = Map.build(12, 12, Map.EMPTY_GRASS);

		map = Map.load("testlong");

		map.setX(Screen.getWidth() / 2 - map.getWidth() / 2);
		map.setY(Screen.getHeight() / 2 - map.getHeight() / 2);
		map.setVisible(true);

		at = new ArrowTower(map, 3, 1);
		
		st = new ArrowTower(map, 15, 1);

		World.add(map, World.BACKGROUND);
		World.add(at, World.FOREGROUND);
		World.add(st, World.FOREGROUND);
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

			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				map.browse(Map.RIGHT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				map.browse(Map.LEFT);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				map.browse(Map.UP);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				map.browse(Map.DOWN);
			}

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
		new Main(new TowerDefense(), "Tower Defense", 800, 600, false);
	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}
	}

}
