package me.funso.angtowerdefense.packet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.funso.angtowerdefense.op.Op;

public class Packet {
	DataInputStream reader = null;
	DataOutputStream writer = null;
	ByteArrayOutputStream bout = null;
	
	public Packet(ByteArrayInputStream in) {
		reader = new DataInputStream(in);
	}
	
	public Packet() {
		bout = new ByteArrayOutputStream();
		writer = new DataOutputStream(bout);
	}
	
	short readShort() throws IOException {
		return reader.readShort();
	}

	public byte readByte() throws IOException {
		return reader.readByte();
	}

	public String readUTF() throws IOException {
		return reader.readUTF();
	}
	
	public byte[] finish() {
		return bout.toByteArray();
	}
	
	public void writeUTF(String str) throws IOException {
		writer.writeUTF(str);
	}

	public void writeByte(int v) throws IOException {
		writer.writeByte(v);
	}
	
	public PacketOpcode readOpcode() throws IOException {
		return PacketOpcode.values()[readByte()];
	}
	
	public void writeOpcode(PacketOpcode op) throws IOException {
		writeByte(op.ordinal());
	}
	
	public void writeOp(Op op) throws IOException {
		new ObjectOutputStream(writer).writeObject(op);
	}
	
	public Op readOp() throws IOException, ClassNotFoundException {
		return (Op) new ObjectInputStream(reader).readObject();
	}
}
