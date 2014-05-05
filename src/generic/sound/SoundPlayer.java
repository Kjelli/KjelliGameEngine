package generic.sound;

import java.io.IOException;

import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundPlayer {
	private void playSound() {
		try {
			AudioLoader
					.getAudio("WAV", ResourceLoader
							.getResourceAsStream("testdata/cbrown01.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
