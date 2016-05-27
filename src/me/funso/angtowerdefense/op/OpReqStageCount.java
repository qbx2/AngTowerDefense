package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqStageCount extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_STAGE_COUNT.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}

	public OpReqStageCount() {
	}
}
