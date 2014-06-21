package no.kjelli.towerdefense.map;

public class MapFactory {
	public static final int EMPTY = 0;

	public static Map generate(int width, int height) {
		return generate(width, height, EMPTY);
	}

	public static Map generate(int width, int height, int template) {
		Map newMap;
		switch (template) {
		default:
		case EMPTY:
			newMap = emptyMap(width, height);
			break;
		}
		return newMap;
	}

	private static Map emptyMap(int width, int height) {
		Map newMap = new Map(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				newMap.setTile(x, y, new GrassTile(newMap, x, y));
			}
		}
		return newMap;
	}
}
