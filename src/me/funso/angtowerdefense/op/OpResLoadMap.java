package me.funso.angtowerdefense.op;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResLoadMap extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_LOAD_MAP.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public String map;
	
	public OpResLoadMap(String data) {
		this.map = data;
	}
}
