package no.kjelli.bombline.network;

public class PacketPlayerUpdateRequest extends Packet {
	public int id;

	public PacketPlayerUpdateRequest() {
	}

	public PacketPlayerUpdateRequest(int id) {
		this.id = id;
	}
}
