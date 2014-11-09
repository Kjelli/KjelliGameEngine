package no.kjelli.onlinetest.network;

public class PacketPlayerAdd extends Packet {
	public int id;

	public PacketPlayerAdd() {
	}

	public PacketPlayerAdd(int id) {
		this.id = id;
	}
}
