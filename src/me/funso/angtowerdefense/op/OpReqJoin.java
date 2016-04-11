package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqJoin extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_JOIN.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public String user_id;
	public String user_pw;
	public String nickname;

	public OpReqJoin(String user_id, String user_pw, String nickname) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.nickname = nickname;
	}
}
