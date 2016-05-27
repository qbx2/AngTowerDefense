package me.funso.angtowerdefense.packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.funso.angtowerdefense.op.Op;

public class PacketWriter {
	public static void write(DataOutputStream out, Packet p) throws IOException {
		byte[] packet_body = p.finish();
		//System.out.println(Arrays.toString(packet_body));
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeShort(packet_body.length);
		dout.write(packet_body);
		out.write(bout.toByteArray());
	}

	public static void writeOp(DataOutputStream dout, Op op) throws IOException {
		Packet p = new Packet();
		p.writeOp(op);
		PacketWriter.write(dout, p);
	}
}
