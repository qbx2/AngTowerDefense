package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpAlert extends Op {
	private static final long serialVersionUID = PacketOpcode.ALERT.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public String message;

	public OpAlert(String message) {
		this.message = message;
	}
}
