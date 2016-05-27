package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResStageCount extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_TOWER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int count;
	
	public OpResStageCount(int count) {
		this.count = count;
	}
}
