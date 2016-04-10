package me.funso.angtowerdefense.op;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.funso.angtowerdefense.packet.PacketOpcode;

public class OpReqLogin extends Op {
	private static final long serialVersionUID = PacketOpcode.REQ_LOGIN.ordinal();
	
	public PacketOpcode getPacketOpcode() {
		return PacketOpcode.values()[(int) serialVersionUID];
	}
	
	public String user_id;
	public String user_pw;

	public OpReqLogin(String user_id, String user_pw) {
		this.user_id = user_id;
		this.user_pw = user_pw;
	}

	public void writeObject(ObjectOutputStream oout) throws IOException {
		oout.writeUTF(user_id);
		oout.writeUTF(user_pw);
	}
	
	public void readObject(ObjectInputStream oin) throws IOException, ClassNotFoundException {
		user_id = oin.readUTF();
		user_pw = oin.readUTF();
	}
}
