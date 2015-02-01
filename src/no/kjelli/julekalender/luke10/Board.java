package no.kjelli.julekalender.luke10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Board {
	private static final Map<String, Tile> board = Collections
			.synchronizedMap(new HashMap<String, Tile>());
	private static final ArrayList<Player> players = new ArrayList<Player>();
	private static final int numPlayers = 800;
	private static int preferredDirection = -1;
	private static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;

	public static void init() {
		new Tile(0, 0);
		for (int i = 0; i < numPlayers; i++) {
			Player p = new Player(Tile.decode(0, 0));
			players.add(p);
			p.start();
		}
		Input.register(new InputListener() {

			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_S || eventKey == Keyboard.KEY_W
						|| eventKey == Keyboard.KEY_A
						|| eventKey == Keyboard.KEY_D)
					preferredDirection = -1;
			}

			@Override
			public void keyDown(int eventKey) {
				if (eventKey == Keyboard.KEY_S)
					preferredDirection = DOWN;
				if (eventKey == Keyboard.KEY_W)
					preferredDirection = UP;
				if (eventKey == Keyboard.KEY_A)
					preferredDirection = LEFT;
				if (eventKey == Keyboard.KEY_D)
					preferredDirection = RIGHT;
			}
		});
	}

	public static void render() {
		ArrayList<Tile> tiles = new ArrayList<Tile>(board.values());
		for (Tile t : tiles) {
			t.draw();
		}
		for (Player p : players) {
			p.draw();
		}
	}

	static class Tile {
		int x, y;
		boolean valid;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
			valid = sumOfCoordinate(x, y) <= 19;
			synchronized (board) {
				board.put(toString(), this);
			}

		}

		private static Tile decode(int x, int y) {
			Tile tile;
			synchronized (board) {
				tile = board.get(x + "," + y);
			}
			return tile;
		}

		private void draw() {
			Draw.fillRect(x * JuleKalender.block_size, y
					* JuleKalender.block_size, 0.0f, JuleKalender.block_size,
					JuleKalender.block_size, valid ? Color.gray : Color.red);
		}

		@Override
		public String toString() {
			return x + "," + y;
		}

	}

	static class Player extends Thread {
		Tile container;

		Player(Tile tile) {
			container = tile;
		}

		boolean attemptTraversal(int dx, int dy) {
			if (Math.abs(dx) > 1 || Math.abs(dy) > 1)
				return false;
			if (Math.abs(dx) > 0 && Math.abs(dy) > 0)
				return false;

			Tile dest = Tile.decode(container.x + dx, container.y + dy);
			if (dest != null) {
				if (dest.valid) {
					container = dest;
					return true;
				} else {
					return false;
				}
			} else {
				Tile newTile = new Tile(container.x + dx, container.y + dy);
				if (newTile.valid) {
					container = newTile;
					return true;
				} else {
					return false;
				}
			}
		}

		private void draw() {
			Draw.fillRect(container.x * JuleKalender.block_size, container.y
					* JuleKalender.block_size, 0.1f, JuleKalender.block_size,
					JuleKalender.block_size, Color.blue);
		}

		@Override
		public void run() {
			while (true) {
				try {
					double d = Math.random();

					switch (preferredDirection) {
					case -1:
						break;
					case LEFT:
						attemptTraversal(-1, 0);
						break;
					case RIGHT:
						attemptTraversal(1, 0);
						break;
					case UP:
						attemptTraversal(0, 1);
						break;
					case DOWN:
						attemptTraversal(0, -1);
						break;
					}

					if (d > 0.8)
						attemptTraversal(1, 0);
					else if (d > 0.6)
						attemptTraversal(-1, 0);
					else if (d > 0.4)
						attemptTraversal(0, 1);
					else if (d > 0.2)
						attemptTraversal(0, -1);

					Thread.sleep(1000 / 120);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private static long sumOfCoordinate(int x, int y) {
		return sumOfDigits(Math.abs(x)) + sumOfDigits(Math.abs(y));
	}

	private static long sumOfDigits(long n) {
		long sum = 0;
		while (n != 0) {
			// add last digit to the sum
			sum += n % 10;
			// cut last digit
			n /= 10;
		}
		return sum;
	}

	public static void dispose() {
		for (Player p : players) {
			p.stop();
		}
	}

	public static void centerOn(int x, int y) {
		Tile dest = Tile.decode(x, y);
		if (dest != null && dest.valid)
			for (Player p : players) {
				p.container = dest;
			}
	}
}
