package no.kjelli.generic.settings;

import java.util.HashMap;

public class Settings {
	private static HashMap<String, Object> settings = new HashMap<String, Object>();

	private Settings() {
	}

	public static void put(String name, Object val) {
		settings.put(name, val);
	}

	public static boolean get(String name, boolean defaultValue) {
		return settings.containsKey(name) ? (boolean) settings.get(name)
				: defaultValue;
	}

	public static int get(String name, int defaultValue) {
		return settings.containsKey(name) ? (int) settings.get(name)
				: defaultValue;
	}

	public static float get(String name, float defaultValue) {
		return settings.containsKey(name) ? (float) settings.get(name)
				: defaultValue;
	}

	public static double get(String name, double defaultValue) {
		return settings.containsKey(name) ? (double) settings.get(name)
				: defaultValue;
	}

	public static long get(String name, long defaultValue) {
		return settings.containsKey(name) ? (long) settings.get(name)
				: defaultValue;
	}

	public static short get(String name, short defaultValue) {
		return settings.containsKey(name) ? (short) settings.get(name)
				: defaultValue;
	}

	public static byte get(String name, byte defaultValue) {
		return settings.containsKey(name) ? (byte) settings.get(name)
				: defaultValue;
	}

	public static String get(String name, String defaultValue) {
		return settings.containsKey(name) ? (String) settings.get(name)
				: defaultValue;
	}

	public static Object get(String name, Object defaultValue) {
		return settings.containsKey(name) ? (Object) settings.get(name)
				: defaultValue;
	}
}
