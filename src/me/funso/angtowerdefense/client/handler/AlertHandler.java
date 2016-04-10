package me.funso.angtowerdefense.client.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.Op;
import me.funso.angtowerdefense.op.OpAlert;
import me.funso.angtowerdefense.packet.PacketOpcode;

public class AlertHandler extends Thread {
	Socket s;
	LinkedBlockingQueue<Op> q;
	
	public AlertHandler(ClientParam param) {
		this.s = param.sock;
		this.q = param.qs.get(PacketOpcode.ALERT);
	}
	
	public void run() {
		try {
			while(true) {
				OpAlert op = (OpAlert)q.take();
				System.out.println(op.message);
				s.close();
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
