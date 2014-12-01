package no.kjelli.bombline.levels;

import java.util.ArrayList;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.gameobjects.PlayerMP;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;

public class Level {
	private ArrayList<GameObject> objects;
	private Player player;
	private ArrayList<PlayerMP> playersMP = new ArrayList<PlayerMP>();

	private int width, height;
	private int maxPlayers;
	private int[] playerSpawnX;
	private int[] playerSpawnY;
	private char[][] map;


	public Level(int width, int height, ArrayList<GameObject> objects, Player player,
			int maxPlayers, int[] playerSpawnX, int[] playerSpawnY, char[][] map) {
		this.player = player;
		this.objects = objects;
		this.maxPlayers = maxPlayers;
		this.playerSpawnX = playerSpawnX;
		this.playerSpawnY = playerSpawnY;
		this.map = map;
		setWidth((int) (width * BombermanOnline.block_size));
		setHeight((int) (height * BombermanOnline.block_size));
	}

	void addObjectsToWorld() {
		for (GameObject b : objects)
			World.add(b);
	}

	public void end() {
		for (GameObject b : objects)
			b.destroy();
		objects.clear();
	}

	private void setHeight(int height) {
		if (height <= 0)
			throw new IllegalArgumentException();
		this.height = height;
	}

	private void setWidth(int width) {
		if (width <= 0)
			throw new IllegalArgumentException();
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Player getPlayer() {
		return player;
	}

	public int getPlayerSpawnX(int id) {
		return playerSpawnX[id - 1];
	}

	public int getPlayerSpawnY(int id) {
		return playerSpawnY[id - 1];
	}

	public ArrayList<PlayerMP> getPlayersMP() {
		return playersMP;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public PlayerMP getPlayerMP(int id) {
		for (PlayerMP p : getPlayersMP()) {
			if (id == p.getID())
				return p;
		}
		return null;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public void newPlayer(PlayerMP newPlayer) {
		playersMP.add(newPlayer);
	}

	public void removePlayer(PlayerMP oldPlayer) {
		playersMP.remove(oldPlayer);
	}
}
