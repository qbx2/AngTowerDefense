package me.funso.angtowerdefense.packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class PacketWriter {
	public static void write(DataOutputStream out, Packet p) throws IOException {
		byte[] packet_body = p.finish();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeShort(packet_body.length);
		dout.write(packet_body);
		out.write(bout.toByteArray());
	}

}
