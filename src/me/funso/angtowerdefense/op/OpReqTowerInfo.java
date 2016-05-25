package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqTowerInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_TOWER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int idx;

	public OpReqTowerInfo(int idx) {
		this.idx = idx;
	}
}
