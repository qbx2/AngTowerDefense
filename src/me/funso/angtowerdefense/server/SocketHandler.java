package me.funso.angtowerdefense.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import me.funso.angtowerdefense.Packet;
import me.funso.angtowerdefense.PacketOpcode;
import me.funso.angtowerdefense.PacketReader;
import me.funso.angtowerdefense.server.handler.Alert;
import me.funso.angtowerdefense.server.handler.Join;
import me.funso.angtowerdefense.server.handler.Login;

public class SocketHandler extends Thread {
	Socket s;
	DataInputStream in;
	DataOutputStream out;
	
	public class Param {
		public Socket sock;
		public DataInputStream in;
		public DataOutputStream dout;
		public Packet packet;
		
		public Param(Socket s, DataInputStream in, DataOutputStream out, Packet p) {
			this.sock = s;
			this.in = in;
			this.dout = out;
			this.packet = p;
		}
	}

	public SocketHandler(Socket s) throws IOException {
		this.s = s;
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
	}
	
	public void run() {
		try {
			while(true) {
				Packet packet = PacketReader.read(in);
				PacketOpcode opcode = packet.readOpcode();
				Param p = new Param(s, in, out, packet);
				
				switch(opcode) {
				case ALERT: // fatal error, extra is UTF8Bytes
					Alert.p(p);
					break;
				case REQLOGIN:
					Login.p(p);
					break;
				case REQJOIN:
					Join.p(p);
					break;
				case REQSTART:
					//startGame(packet);
					break;
				case REQSAVE:
					//saveGame(packet);
					break;
				case REQLOAD:
					//loadGame(packet);
					break;
				case INSTALLTOWER:
					//installTower(packet);
					break;
				case REQUPGRADE:
					//upgrade(packet);
					break;
				default:
					// not implemented
					break;
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		
		try {
			s.close();
		} catch (IOException e) {
		}
	}
}
