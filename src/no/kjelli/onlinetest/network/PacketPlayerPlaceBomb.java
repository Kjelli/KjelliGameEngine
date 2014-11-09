package no.kjelli.onlinetest.network;

public class PacketPlayerPlaceBomb extends Packet{
	public int id;
	public int x, y;
	public PacketPlayerPlaceBomb() {
	}

	public PacketPlayerPlaceBomb(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
}
