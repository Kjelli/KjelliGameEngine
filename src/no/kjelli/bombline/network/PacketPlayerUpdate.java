package no.kjelli.bombline.network;

public class PacketPlayerUpdate extends Packet{
	public int id;
	public float x;
	public float y;
	public int directionID;
	public int animationID;

	public PacketPlayerUpdate() {
	}

	public PacketPlayerUpdate(int id, float x, float y, int direction, int animation) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.directionID = direction;
		this.animationID = animation;
	}
}
