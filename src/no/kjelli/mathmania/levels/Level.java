package no.kjelli.mathmania.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.mathmania.gameobjects.Block;
import no.kjelli.mathmania.gameobjects.DivideBlock;
import no.kjelli.mathmania.gameobjects.MultiplyBlock;
import no.kjelli.mathmania.gameobjects.Player;
import no.kjelli.mathmania.gameobjects.ObstructingBlock;

public class Level extends AbstractGameObject {

	public static final char FLOOR = ' ', BLOCK = '#', MULTIPLY = 'M',
			DIVIDE = 'D', PLAYER = 'P';

	public ArrayList<Block> blocks;

	public Player player;

	public Level(String name) {
		super(0, 0, 0, 0);
		loadFromFile(name);
	}

	@Override
	public void onCreate() {
		for (Block b : blocks)
			World.add(b);
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
		player.setVisible(visible);
		for (Block b : blocks)
			b.setVisible(visible);
	}

	public void start() {
		World.add(player);
		World.add(this);
		World.init((int)getWidth(), (int)getHeight());
		setVisible(true);
	}

	public void end() {
		player.destroy();
		for (Block b : blocks)
			b.destroy();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public void draw() {
	}

	public void loadFromFile(String name) {
		BufferedReader br = null;
		blocks = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("res\\" + name + ".lev"));

			int width = Integer.parseInt(br.readLine());
			int height = Integer.parseInt(br.readLine());
			int difficulty = Integer.parseInt(br.readLine());

			setWidth(width * Block.SIZE);
			setHeight(height * Block.SIZE);

			int x = 0;
			int y = 0;

			String line;
			while ((line = br.readLine()) != null) {
				x = 0;
				for (char c : line.toCharArray()) {
					if (x > width || y > height) {
						System.err
								.println("Inconsistent dimensions in lev file!");
						br.close();
						return;
					}
					switch (c) {
					case BLOCK:
						blocks.add(new ObstructingBlock(x, height - 1 - y, this));
						break;
					case MULTIPLY:
						blocks.add(new MultiplyBlock(x, height - 1 - y, difficulty, this));
						break;
					case DIVIDE:
						blocks.add(new DivideBlock(x, height - 1 - y, difficulty, this));
						break;
					case FLOOR:
						break;
					case PLAYER:
						player = new Player(x * Block.SIZE + Player.SIZE / 2, (height - 1 - y) * Block.SIZE + Player.SIZE / 2, this);
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
}
