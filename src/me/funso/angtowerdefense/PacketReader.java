package me.funso.angtowerdefense;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PacketReader {

	public static Packet read(DataInputStream in) throws IOException {
		short packet_length = in.readShort();
		byte packet_body[] = new byte[packet_length];
		in.read(packet_body);
		return new Packet(new ByteArrayInputStream(packet_body));
	}

}
