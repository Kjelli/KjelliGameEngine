package no.kjelli.generic.input;

import java.util.HashSet;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

public class Input {

	static HashSet<InputListener> listeners = new HashSet<>();
	static HashSet<InputListener> removeQueue = new HashSet<>();
	static HashSet<InputListener> addQueue = new HashSet<>();
	private static boolean wasRecentlyCleared = false;
	private static boolean shift = false;
	private static boolean control = false;

	public static void pollInput() {
		if (addQueue.size() > 0) {
			listeners.addAll(addQueue);
			addQueue.clear();
		}
		if (removeQueue.size() > 0) {
			listeners.removeAll(removeQueue);
			removeQueue.clear();
		}
		Keyboard.poll();
		while (Keyboard.next()) {
			if (wasRecentlyCleared) {
				wasRecentlyCleared = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT
					|| Keyboard.getEventKey() == Keyboard.KEY_RSHIFT) {
				shift = Keyboard.getEventKeyState();
			} else if (Keyboard.getEventKey() == Keyboard.KEY_LCONTROL
					|| Keyboard.getEventKey() == Keyboard.KEY_RCONTROL) {
				control = Keyboard.getEventKeyState();
			}
			for (InputListener l : listeners) {
				if (Keyboard.getEventKeyState()) {
					l.keyDown(Keyboard.getEventKey());
				} else {
					l.keyUp(Keyboard.getEventKey());
				}
				if (wasRecentlyCleared) {
					wasRecentlyCleared = false;
					return;
				}
			}

		}
	}

	public static void register(InputListener listener) {
		addQueue.add(listener);
	}

	public static void unregister(InputListener listener) {
		removeQueue.add(listener);
	}

	public static void clear() {
		addQueue.clear();
		removeQueue.clear();
		listeners.clear();
		wasRecentlyCleared = true;
	}

	public static boolean shift() {
		return shift;
	}

	public static boolean control() {
		return control;
	}

}
