package no.kjelli.mathmania.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.mathmania.gameobjects.Player;
import no.kjelli.mathmania.gameobjects.blocks.AddBlock;
import no.kjelli.mathmania.gameobjects.blocks.Block;
import no.kjelli.mathmania.gameobjects.blocks.DivideBlock;
import no.kjelli.mathmania.gameobjects.blocks.MultiplyBlock;
import no.kjelli.mathmania.gameobjects.blocks.ObstructingBlock;
import no.kjelli.mathmania.gameobjects.blocks.SubtractBlock;
import no.kjelli.mathmania.gameobjects.collectibles.Coin;

public class Level {
	public static final char FLOOR = '.', BLOCK = '#', ADD = 'A',
			SUBTRACT = 'S', MULTIPLY = 'M', DIVIDE = 'D', PLAYER = 'P',
			COIN = 'C';

	private static int width, height;
	private static int difficulty;
	private static ArrayList<GameObject> blocks;

	private static Player player;

	public static void init(String filename) {
		loadFromFile(filename);
		for (GameObject b : blocks)
			World.add(b, World.BACKGROUND);
	}

	public static void start() {
		World.add(player);
		World.init((int) getWidth(), (int) getHeight());
	}

	public static void end() {
		player.destroy();
		for (GameObject b : blocks)
			b.destroy();
	}

	public void draw() {
	}

	public static void loadFromFile(String name) {
		BufferedReader br = null;
		blocks = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("res\\" + name + ".lev"));

			int width = Integer.parseInt(br.readLine());
			int height = Integer.parseInt(br.readLine());
			difficulty = Integer.parseInt(br.readLine());

			setWidth((int) (width * Block.SIZE));
			setHeight((int) (height * Block.SIZE));

			int x = 0;
			int y = 0;

			String line;
			while ((line = br.readLine()) != null) {
				x = 0;
				for (String c : line.split(" ")) {
					if (x > width || y > height) {
						System.err
								.println("Inconsistent dimensions in lev file!");
						br.close();
						return;
					}

					int difficultyModifier = 1;
					if (c.length() > 1) {
						difficultyModifier = Integer.parseInt(c.substring(1,
								c.length()));
					}

					switch (c.charAt(0)) {
					case BLOCK:
						blocks.add(new ObstructingBlock(x, height - 1 - y));
						break;
					case MULTIPLY:
						blocks.add(new MultiplyBlock(x, height - 1 - y,
								difficulty * difficultyModifier));
						break;
					case DIVIDE:
						blocks.add(new DivideBlock(x, height - 1 - y,
								difficulty * difficultyModifier));
						break;
					case ADD:
						blocks.add(new AddBlock(x, height - 1 - y, difficulty
								* difficultyModifier));
						break;
					case SUBTRACT:
						blocks.add(new SubtractBlock(x, height - 1 - y,
								difficulty * difficultyModifier));
						break;
					case COIN:
						blocks.add(new Coin(x * Block.SIZE, (height - 1 - y)
								* Block.SIZE, difficultyModifier));
						break;
					case FLOOR:
						break;
					case PLAYER:
						player = new Player(x * Block.SIZE + Player.SIZE / 2,
								(height - 1 - y) * Block.SIZE + Player.SIZE / 2);
						break;
					default:
						break;
					}
					x++;
				}
				y++;
			}

			return;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void setHeight(int height) {
		if (height <= 0)
			throw new IllegalArgumentException();
		Level.height = height;
	}

	private static void setWidth(int width) {
		if (width <= 0)
			throw new IllegalArgumentException();
		Level.width = width;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static Player getPlayer() {
		return player;
	}

	public static int getDifficulty() {
		return difficulty;
	}
}
