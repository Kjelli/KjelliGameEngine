package no.kjelli.tweentest;

import java.io.IOException;
import java.util.Scanner;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.generic.tweens.GameObjectAccessor;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;

public class TweenTest implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 32;
	public static long ticks = 0;

	public static STATE state;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	@Override
	public void init() {
		loadSounds();
		initIntro();
	}

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

	public void initIntro() {
		World.clear();
		state = STATE.INTRO;

		GameObject to = new TweenObject(Screen.getCenterX(),
				Screen.getCenterY());
		World.add(to);
		Timeline.createParallel()
				.push(Tween.to(to, GameObjectAccessor.SCALE_WH, 1500).target(
						Screen.getWidth(), Screen.getHeight()))
				.push(Tween.to(to, GameObjectAccessor.POSITION_XY, 1500)
						.target(0, 0))
				.push(Timeline
						.createSequence()
						.push(Tween.to(to, GameObjectAccessor.SCALE_W, 1500)
								.target(Screen.getCenterX()).delay(1500))
						.push(Tween.to(to, GameObjectAccessor.SCALE_W, 1500)
								.target(Screen.getWidth()))
						.push(Tween.to(to, GameObjectAccessor.SCALE_H, 500)
								.target(50))
						.push(Tween.to(to, GameObjectAccessor.SCALE_W, 500)
								.target(200))
						.push(Tween.to(to, GameObjectAccessor.POSITION_XY, 500)
								.target(Screen.getCenterX() - 100,
										Screen.getCenterY()))
						.push(Timeline
								.createParallel()
								.push(Tween
										.to(to, GameObjectAccessor.ROTATION,
												5000).target(360)
										.ease(Elastic.INOUT))
								.push(Tween
										.to(to, GameObjectAccessor.POSITION_Y,
												5000).target(100)
										.ease(Elastic.INOUT))))
				.start(GameWrapper.tweenManager);

	}

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

	}

	@Override
	public void render() {
		Screen.render();
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

	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}

	}

	@Override
	public double getWidth() {
		return 800;
	}

	@Override
	public double getHeight() {
		return 600;
	}

	@Override
	public String getTitle() {
		return "TweenTest";
	}

	public static void main(String[] args) {
		new GameWrapper(new TweenTest(), false);
	}

}
