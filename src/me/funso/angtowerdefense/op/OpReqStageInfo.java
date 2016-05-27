package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqStageInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_STAGE_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int idx;

	public OpReqStageInfo(int idx) {
		this.idx = idx;
	}
}
