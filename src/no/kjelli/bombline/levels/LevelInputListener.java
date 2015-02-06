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
<<<<<<< HEAD
			LevelWrapper.end();
			BombermanOnline.initIntro();
		} else if (eventKey == Keyboard.KEY_SPACE
				&& BombermanOnline.state == STATE.WAITING_FOR_PLAYERS) {
			if (Network.isHosting()) {
=======
			BombermanOnline.reset();
		} else if (eventKey == Keyboard.KEY_SPACE) {
			if (Network.isHosting()
					&& BombermanOnline.state == STATE.WAITING_FOR_PLAYERS) {
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
				BombermanOnline.state = STATE.PLAYING;
				LevelWrapper.start();
				Network.getServer().sendToAllExceptTCP(
						Network.getClient().getID(), new PacketLevelStart());
			}
		}
<<<<<<< HEAD
		if (eventKey == Keyboard.KEY_V) {
=======
		if (eventKey == Keyboard.KEY_V
				&& BombermanOnline.state == STATE.PLAYING) {
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			LevelWrapper.getLevel().getPlayer().displayName(false);
			for (Player p : LevelWrapper.getLevel().getPlayersMP()) {
				p.displayName(false);
			}
		}
<<<<<<< HEAD
=======
		if (eventKey == Keyboard.KEY_V
				&& BombermanOnline.state == STATE.PLAYING) {
			LevelWrapper.toggleNameDisplay();
		}
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

	}

	@Override
	public void keyDown(int eventKey) {
<<<<<<< HEAD
		if (eventKey == Keyboard.KEY_V) {
			LevelWrapper.getLevel().getPlayer().displayName(true);
			for (Player p : LevelWrapper.getLevel().getPlayersMP()) {
				p.displayName(true);
			}
		}

=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

}
