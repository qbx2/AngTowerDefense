package me.funso.angtowerdefense.op;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpResJoin extends Op {
	private static final long serialVersionUID = PacketOpcode.RES_JOIN.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public int errorCode;
	public String message;

	public OpResJoin(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public void writeObject(ObjectOutputStream oout) throws IOException {
		oout.writeInt(errorCode);
		oout.writeUTF(message);
	}
	
	public void readObject(ObjectInputStream oin) throws IOException, ClassNotFoundException {
		errorCode = oin.readInt();
		message = oin.readUTF();
	}
}
