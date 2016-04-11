package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResJoin extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_JOIN.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int errorCode;
	public String message;

	public OpResJoin(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
}
