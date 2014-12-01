package no.kjelli.bombline.network;

public class PacketLevelResponse extends Packet {
	public int receiverID;
	public char[][] levelMap;
	public int maxPlayers;

	public PacketLevelResponse() {

	}

	public PacketLevelResponse(int id, char[][] levelMap, int maxPlayers) {
		this.receiverID = id;
		this.levelMap = levelMap;
		this.maxPlayers = maxPlayers;
	}
}
