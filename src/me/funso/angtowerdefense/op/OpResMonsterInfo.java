package me.funso.angtowerdefense.op;
import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResMonsterInfo extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_MONSTER_INFO.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public MonsterInfo info;
	
	public OpResMonsterInfo(MonsterInfo info) {
		this.info = info;
	}
}
