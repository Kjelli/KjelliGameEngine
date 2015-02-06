package no.kjelli.bombline.network;

public class PacketPlayerLoseLife extends Packet {
	public int id;
	public PacketPlayerLoseLife() {
	}

	public PacketPlayerLoseLife(int id) {
		this.id = id;
	}
}
