package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpAlert;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;

public class Alert {
	public static void p(ClientParam param, String message) throws IOException {
		Packet p = new Packet();
		p.writeOp(new OpAlert(message));
		PacketWriter.write(param.dout, p);
	} 
}