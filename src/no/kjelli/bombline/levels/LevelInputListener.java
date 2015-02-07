package no.kjelli.bombline.levels;

import org.lwjgl.input.Keyboard;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.BombermanOnline.STATE;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.PacketLevelStart;
import no.kjelli.generic.input.InputListener;

public class LevelInputListener implements InputListener {

	@Override
	public void keyUp(int eventKey) {
		if (eventKey == Keyboard.KEY_F2) {
			BombermanOnline.reset();
		} else if (eventKey == Keyboard.KEY_SPACE) {
			if (Network.isHosting()
					&& BombermanOnline.state == STATE.WAITING_FOR_PLAYERS) {
				BombermanOnline.state = STATE.PLAYING;
				LevelWrapper.start();
				Network.getServer().sendToAllExceptTCP(
						Network.getClient().getID(), new PacketLevelStart());
			}
		}

		if (eventKey == Keyboard.KEY_V
				&& BombermanOnline.state == STATE.PLAYING) {
			LevelWrapper.toggleNameDisplay();
		}

	}

	@Override
	public void keyDown(int eventKey) {

	}

}
