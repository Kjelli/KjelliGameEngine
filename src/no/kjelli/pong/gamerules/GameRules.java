package no.kjelli.pong.gamerules;

public abstract class GameRules {
	
	// TODO class. might discard
	
	public static enum GAME_TYPE {
		FIRST_TO
	}

	private final GAME_TYPE game_type;

	public GameRules(GAME_TYPE game_type) {
		this.game_type = game_type;
	}
}
