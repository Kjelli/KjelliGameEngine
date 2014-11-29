package no.kjelli.bombline.network;

public class PacketPlayerRemove extends Packet{
	public int id;

	public PacketPlayerRemove() {
	}

	public PacketPlayerRemove(int id) {
		this.id = id;
	}
}
