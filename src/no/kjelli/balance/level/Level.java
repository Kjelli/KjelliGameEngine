package no.kjelli.balance.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import no.kjelli.balance.gameobjects.Hoop;
import no.kjelli.generic.gfx.Screen;

public class Level {
	ArrayList<SubLevel> subLevels;

	public Level(LevelEnum level) {
		subLevels = new ArrayList<>();
		loadFromFile(level);
	}

	public int getSublevels() {
		return subLevels.size();
	}

	public void loadFromFile(LevelEnum level) {
		String filepath = level.getFilepath();
		File levelFile = new File(filepath);
		if (!levelFile.exists()) {
			System.err.println("File not found " + filepath);
			return;
		}
		Scanner scanner = null;

		try {
			scanner = new Scanner(levelFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (scanner == null)
			return;

		StringBuilder rawData = new StringBuilder();
		while (scanner.hasNext()) {
			String line = scanner.nextLine();

			if (line.length() > 1 && scanner.hasNext()) {
				rawData.append(line + "\n");
			} else {
				SubLevel sl = new SubLevel();
				if (!sl.makeHoops(rawData.toString())) {
					System.err.println("Aborted level creation!");
					return;
				}
				subLevels.add(sl);
				rawData.delete(0, rawData.length());
			}
		}

		scanner.close();
	}

	public SubLevel getSublevel(int sublevel_index) {
		return subLevels.get(sublevel_index);
	}

	public void printAll() {
		for (SubLevel sl : subLevels) {
			for (Hoop hoop : sl.getHoops()) {
				System.out.println(hoop);
			}
		}
	}

	public class SubLevel {
		Scanner hoopDataReader;
		Hoop[] hoops;

		private boolean makeHoops(String data) {
			hoopDataReader = new Scanner(data);
			int count = 0;
			while (hoopDataReader.hasNext()) {
				hoopDataReader.nextLine();
				count++;
			}
			hoops = new Hoop[count];
			hoopDataReader.close();
			hoopDataReader = new Scanner(data);

			count = 0;
			while (hoopDataReader.hasNextLine()) {
				float x, y, length;
				int alignment;
				String line = hoopDataReader.nextLine();
				String[] elements = line.split("[,]");
				try {
					x = Float.parseFloat(elements[0]) * Screen.getWidth();
					y = Float.parseFloat(elements[1]) * Screen.getHeight();
					length = Float.parseFloat(elements[2]);
					alignment = Integer.parseInt(elements[3]);
				} catch (NumberFormatException nfe) {
					System.err.println("Invalid level details: " + line);
					return false;
				}
				hoops[count++] = new Hoop(x, y, length, alignment);
			}
			hoopDataReader.close();
			return true;
		}

		public Hoop[] getHoops() {
			return hoops;
		}
	}

	public static void main(String[] args) {
		new Level(LevelEnum.easy).printAll();
	}

}
