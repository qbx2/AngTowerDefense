package me.funso.angtowerdefense.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.Op;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class ClientParam extends Param {
	public HashMap<PacketOpcode, LinkedBlockingQueue<Op>> qs;
	
	public ClientParam(Socket s, DataInputStream din, DataOutputStream dout, HashMap<PacketOpcode, LinkedBlockingQueue<Op>> qs) {
		super(s, din, dout);
		this.qs = qs;
	}
}
