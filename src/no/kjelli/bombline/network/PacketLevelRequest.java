package no.kjelli.bombline.network;

public class PacketLevelRequest extends Packet {
	public int receiverID;

	public PacketLevelRequest() {

	}

	public PacketLevelRequest(int id) {
		this.receiverID = id;
	}
}
