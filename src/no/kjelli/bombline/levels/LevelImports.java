package no.kjelli.bombline.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import no.kjelli.bombline.gameobjects.Block;
import no.kjelli.bombline.gameobjects.Destructible;
import no.kjelli.bombline.gameobjects.Floor;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.PacketLevelResponse;
import no.kjelli.generic.gameobjects.GameObject;

public class LevelImports {
	private static ArrayList<GameObject> objects;
	public static final char FLOOR = '.', BLOCK = '#', DESTRUCTIBLE = 'X',
			PLAYER_ONE = '1', PLAYER_TWO = '2', PLAYER_THREE = '3',
			PLAYER_FOUR = '4';
	private static int width, height, maxPlayers;
	private static Player player;
	private static int[] playerSpawnX;
	private static int[] playerSpawnY;
	private static char[][] map;

	public static Level loadFromFile(String name) {
		BufferedReader br = null;
		objects = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("res\\levels\\" + name
					+ ".bml"));
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
			maxPlayers = Integer.parseInt(br.readLine());
			playerSpawnX = new int[maxPlayers];
			playerSpawnY = new int[maxPlayers];
			map = new char[width][height];

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
						return null;
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
								System.out.println("Undefined key: " + key
										+ ", val: " + val);
							}
						}
					} else {
						blocktype = block.charAt(0);
					}
					determineBlock(blocktype, x, (height - y - 1));
					// TODO if ever adding properties to blocks, change this
					map[x][height - y - 1] = blocktype;
					x++;
				}
				y++;
			}
			return buildLevel();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static Level loadFromMap(PacketLevelResponse packet) {
		map = packet.levelMap;
		width = map.length;
		height = map[0].length;
		maxPlayers = packet.maxPlayers;
		playerSpawnX = new int[maxPlayers];
		playerSpawnY = new int[maxPlayers];
		objects = new ArrayList<>();
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				char blocktype = map[x][y];
				determineBlock(blocktype, x, y);
			}
		}
		return buildLevel();
	}

	private static void determineBlock(char blocktype, int x, int y) {
		switch (blocktype) {
		case DESTRUCTIBLE:
			objects.add(new Destructible(x, y));
			break;
		case BLOCK:
			objects.add(new Block(x, y));
			break;
		case PLAYER_ONE:
			addPlayerSpawn(1, x, y);
			break;
		case PLAYER_TWO:
			addPlayerSpawn(2, x, y);
			break;
		case PLAYER_THREE:
			addPlayerSpawn(3, x, y);
			break;
		case PLAYER_FOUR:
			addPlayerSpawn(4, x, y);
			break;
		case FLOOR:
			objects.add(new Floor(x, y));
			break;
		default:
			break;
		}
	}

	private static void addPlayerSpawn(int playerNo, int x, int y) {
		playerSpawnX[playerNo - 1] = x;
		playerSpawnY[playerNo - 1] = y;
		if (Network.isHosting() && playerNo == 1) {
			player = new Player(x, y);
			objects.add(player);
		} else if (Network.isOnline()
				&& playerNo == Network.getClient().getID()) {
			player = new Player(x, y);
			objects.add(player);
		}
		objects.add(new Floor(x, y));
	}

	private static Level buildLevel() {
		Level newLevel = new Level(width, height, objects, player, maxPlayers,
				playerSpawnX, playerSpawnY, map);
		return newLevel;

	}
}
