package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResStageInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_TOWER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public StageInfo info;
	
	public OpResStageInfo(StageInfo info) {
		this.info = info;
	}
}
