package me.funso.angtowerdefense.op;

import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResLogin extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_LOGIN.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int errorCode;
	public String message;
	public User user;

	public OpResLogin(int errorCode, String message, User user) {
		this.errorCode = errorCode;
		this.message = message;
		this.user = user;
	}
}
