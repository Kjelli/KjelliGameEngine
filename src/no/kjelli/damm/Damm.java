package no.kjelli.damm;

import java.io.IOException;

import javax.swing.JOptionPane;

import no.kjelli.balance.gameobjects.BGStar;
import no.kjelli.balance.gameobjects.Wall;
import no.kjelli.damm.board.Board;
import no.kjelli.damm.board.Move;
import no.kjelli.damm.board.Tile;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.input.Keyboard;

public class Damm implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static STATE state;

	public static long ticks = 0;

	public static final Board board = new Board(8, 8);

	private int cooldown = 0;

	@Override
	public void init() {
		loadSounds();
		initIntro();
		initGame();
	}

	public void initIntro() {
		Screen.clearGUI();
		World.clear();
		state = STATE.INTRO;
	}

	public void initGame() {
		Screen.clearGUI();
		World.clear();
		state = STATE.PLAYING;

		initBackground();

		board.setX(Screen.getWidth() / 2 - (board.columns * Tile.SIZE / 2));
		board.setY(Screen.getHeight() / 2 - (board.rows * Tile.SIZE / 2));
		board.generate();
		board.placePieces();
		board.setVisible(true);

		World.add(board);
	}

	private static void initBackground() {
		for (int i = 0; i < 500; i++) {
			float newX = (float) (Math.random() * Screen.getWidth());
			float newY = (float) (Math.random() * Screen.getHeight());
			BGStar s = new BGStar(newX, newY);
			s.setVisible(true);
			World.add(s, World.BACKGROUND);
		}
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
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				// inputMove();
			}
			break;
		default:
			break;

		}
	}

	private void inputMove() {
		// TODO: Puts the whole program to sleep, bad way to prompt input.
		String src = JOptionPane.showInputDialog("Move from: (N#)");
		if (src == null)
			return;
		// TODO: Puts the whole program to sleep, bad way to prompt input.
		String dest = JOptionPane.showInputDialog("Move to: (N#)");
		if (dest == null)
			return;

		Move move = Move.parseMove(src, dest);
		if (move != null) {
			move.make();
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
			if (World.size() < 500) {
				BGStar s = new BGStar(
						Wall.SIZE
								+ (float) (Math.random() * Screen.getWidth() - 2 * Wall.SIZE),
						Screen.getHeight() + 5);
				s.setVisible(true);
				World.add(s, World.BACKGROUND);
			}
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
		new Main(new Damm(), "Damm - by Kjelli", 1366, 768, true);
	}

}
