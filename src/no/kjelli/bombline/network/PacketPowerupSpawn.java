package no.kjelli.bombline.network;

public class PacketPowerupSpawn extends Packet {
	public int type; // ie. Powerup.BOMB
	public int x, y;

	public PacketPowerupSpawn() {
	}

	public PacketPowerupSpawn(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
}
