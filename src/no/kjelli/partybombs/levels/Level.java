package no.kjelli.partybombs.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.partybombs.Partybombs;
import no.kjelli.partybombs.gameobjects.Block;
import no.kjelli.partybombs.gameobjects.Destructible;
import no.kjelli.partybombs.gameobjects.Floor;
import no.kjelli.partybombs.gameobjects.Player;

public class Level {
	public static final char FLOOR = '.', BLOCK = '#', DESTRUCTIBLE = 'X', PLAYER = 'P';

	private static int width, height;
	private static ArrayList<GameObject> objects;
	private static Player player;

	public static void init(String filename) {
		loadFromFile(filename);
		for (GameObject b : objects)
			World.add(b);
		World.pause(Partybombs.tag_playfield, true);
	}

	public static void start() {
		World.init((int) getWidth(), (int) getHeight());
		World.pause(Partybombs.tag_playfield, false);
	}

	public static void end() {
		for (GameObject b : objects)
			b.destroy();
	}

	public void draw() {
	}

	private static void loadFromFile(String name) {
		BufferedReader br = null;
		objects = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("res\\levels\\" + name + ".bml"));
			int width = Integer.parseInt(br.readLine());
			int height = Integer.parseInt(br.readLine());

			setWidth((int) (width * Partybombs.block_size));
			setHeight((int) (height * Partybombs.block_size));

			int x = 0;
			int y = 0;

			String line;
			while ((line = br.readLine()) != null) {
				x = 0;
				for (String block : line.split(" ")) {
					if (x > width || y > height) {
						System.err
								.println("Inconsistent dimensions in lev file!");
						br.close();
						return;
					}
					char blocktype = ' ';
					if (block.length() > 1) {
						System.out.println("parsing " + block);
						String[] properties = block.split(",");
						for (int num = 0; num < properties.length; num++) {
							if (num == 0) {
								blocktype = properties[0].charAt(0);
								continue;
							}
							System.out.println("parsing " + properties[num]);
							String[] elements = properties[num].split("=");
							String key = elements[0];
							String val = elements[1];
							switch (key) {
							default:
								System.out.println("Undefined key: " + key +", val: " + val);
							}
						}
					} else {
						blocktype = block.charAt(0);
					}
					switch (blocktype) {
					case DESTRUCTIBLE:
						objects.add(new Destructible(x, (height - 1 - y)));
						break;
					case BLOCK:
						objects.add(new Block(x, (height - 1 - y)));
						break;
					case PLAYER:
						player = new Player(x, (height - 1 - y));
						objects.add(player);
					case FLOOR:
						objects.add(new Floor(x, (height - 1 - y)));
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
	
	public static Player getPlayer(){
		return player;
	}
}
