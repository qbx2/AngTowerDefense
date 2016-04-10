package me.funso.angtowerdefense.server.handler;

import java.io.IOException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpAlert;
import me.funso.angtowerdefense.server.Server;

public class AlertHandler {
	public static void p(Param param, OpAlert op) throws IOException {
		System.out.println(Server.session.get(param.sock).user_id + " alerts:");
		System.out.println(op.message);
		param.sock.close();
	}
}
