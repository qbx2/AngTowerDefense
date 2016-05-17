package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqLoadMap extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_LOAD_MAP.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int idx;

	public OpReqLoadMap(int idx) {
		this.idx = idx;
	}
}
