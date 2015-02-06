package no.kjelli.platformer.gravity;

public class Gravity {
	private static float GRAVITATION = -0.44f;
	private static float TERMINAL_VELOCITY = 40 * GRAVITATION;

	public static float getGravitation() {
		return GRAVITATION;
	}
	
	public static float getTerminalVelocity() {
		return TERMINAL_VELOCITY;
	}
	
	

}
