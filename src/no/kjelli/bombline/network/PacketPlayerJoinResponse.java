package no.kjelli.bombline.network;

public class PacketPlayerJoinResponse extends Packet{
	boolean accepted;
	
	public PacketPlayerJoinResponse(){
		
	}
	
	public PacketPlayerJoinResponse(boolean accepted){
		this.accepted = accepted;
	}
}
