package me.funso.angtowerdefense.op;

import java.io.Serializable;

import me.funso.angtowerdefense.packet.PacketOpcode;

public abstract class Op implements Serializable {
	private static final long serialVersionUID = -1L;
	
	public abstract PacketOpcode getPacketOpcode();
}
