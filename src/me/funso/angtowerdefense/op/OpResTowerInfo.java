package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResTowerInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_TOWER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public TowerInfo info;
	
	public OpResTowerInfo(TowerInfo info) {
		this.info = info;
	}
}
