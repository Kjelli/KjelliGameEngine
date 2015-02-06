package no.kjelli.bombline.network;

public class PacketPlayerJoinRequest extends Packet{
	int id;
	
	public PacketPlayerJoinRequest() {

	}

	public PacketPlayerJoinRequest(int id) {
		this.id = id;
	}
}
