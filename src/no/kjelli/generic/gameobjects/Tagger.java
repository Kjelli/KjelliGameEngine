package no.kjelli.generic.gameobjects;

public class Tagger {
	private static int nextTagId = 1;

	public static int uniqueTag() {
		if (nextTagId == 0)
			return ++nextTagId;
		return nextTagId <<= 1;
	}
}
