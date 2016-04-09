package me.funso.angtowerdefense.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import me.funso.angtowerdefense.Packet;
import me.funso.angtowerdefense.PacketOpcode;
import me.funso.angtowerdefense.PacketReader;
import me.funso.angtowerdefense.PacketWriter;

public class Client {
	private Socket s = null;
	protected DataOutputStream dout = null;
	protected DataInputStream din = null;
	protected HashMap<PacketOpcode, LinkedBlockingQueue<Packet>> qs = new HashMap<PacketOpcode, LinkedBlockingQueue<Packet>>(); 
	
	private class PacketReceiver extends Thread {
		public PacketReceiver() {
			qs.clear();
			
			for(PacketOpcode op: PacketOpcode.values()) {
				qs.put(op, new LinkedBlockingQueue<Packet>());
			}
		}
		
		public void run() {			
			try {
				while(true) {
					Packet p = PacketReader.read(din);
					PacketOpcode op = p.readOpcode();
					LinkedBlockingQueue<Packet> q = qs.get(op);
					
					//synchronized(q) {
					q.put(p);
					//}
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void alert(String message) throws IOException {
		Packet p = new Packet();
		p.writeOpcode(PacketOpcode.ALERT);
		p.writeUTF(message);
		PacketWriter.write(dout, p);
	}
	
	public void login(String user_id, String user_pw) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOpcode(PacketOpcode.REQLOGIN);
		p.writeUTF(user_id);
		p.writeUTF(user_pw);
		PacketWriter.write(dout, p);
		
		System.out.println(qs.get(PacketOpcode.RESLOGIN));
		p = qs.get(PacketOpcode.RESLOGIN).take();
		System.out.println(p.readUTF());
	}
	
	public void join(String user_id, String user_pw, String nickname) throws IOException {
		Packet p = new Packet();
		p.writeOpcode(PacketOpcode.REQJOIN);
		p.writeUTF(user_id);
		p.writeUTF(user_pw);
		p.writeUTF(nickname);
		PacketWriter.write(dout, p);
	}
	
	public Client(String hostname, int port) throws UnknownHostException, IOException {
		s = new Socket(hostname, port);
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());

		new PacketReceiver().start();
	}

	public void close() throws IOException {
		s.close();
	}
}
