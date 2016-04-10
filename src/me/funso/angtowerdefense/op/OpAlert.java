package me.funso.angtowerdefense.op;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpAlert extends Op {
	private static final long serialVersionUID = PacketOpcode.ALERT.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public String message;

	public OpAlert(String message) {
		this.message = message;
	}

	public void writeObject(ObjectOutputStream oout) throws IOException {
		oout.writeUTF(message);
	}
	
	public void readObject(ObjectInputStream oin) throws IOException, ClassNotFoundException {
		message = oin.readUTF();
	}
}
