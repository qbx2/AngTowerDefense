package me.funso.angtowerdefense.server.handler;

import java.io.IOException;

import me.funso.angtowerdefense.server.SocketHandler.Param;

public class Alert {
	public static void p(Param param) throws IOException {
		System.out.println(param.packet.readUTF());
		param.sock.close();
	}
}
