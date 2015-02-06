package no.kjelli.bombline.network;

public class PacketPowerupGain extends Packet {
	public int id;
	public int type; // ie. Powerup.BOMB
	public int x_index, y_index;

	public PacketPowerupGain() {
	}

	public PacketPowerupGain(int id, int type, int x, int y) {
		this.id = id;
		this.type = type;
		this.x_index = x;
		this.y_index = y;
	}
}
