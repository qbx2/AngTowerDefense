package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqMonsterInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_MONSTER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int idx;

	public OpReqMonsterInfo(int idx) {
		this.idx = idx;
	}
}
