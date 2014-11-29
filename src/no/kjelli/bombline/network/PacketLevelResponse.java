package no.kjelli.bombline.network;

public class PacketLevelResponse extends Packet {
	public int receiverID;
	public char[][] levelMap;

	public PacketLevelResponse() {

	}

	public PacketLevelResponse(int id, char[][] levelMap) {
		this.receiverID = id;
		this.levelMap = levelMap;
	}
}
