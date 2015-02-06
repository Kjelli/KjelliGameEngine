package no.kjelli.bombline.network;

public class PacketPlayerCredentials extends Packet {
	public int id;
	public String name;
	public boolean firstPacket;

	public PacketPlayerCredentials() {
	}

	public PacketPlayerCredentials(int id, String name, boolean firstPacket) {
		this.id = id;
		this.name = name;
		this.firstPacket = firstPacket;
	}
}
