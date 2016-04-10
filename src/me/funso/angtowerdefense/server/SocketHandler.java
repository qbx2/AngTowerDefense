package me.funso.angtowerdefense.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.Op;
import me.funso.angtowerdefense.op.OpAlert;
import me.funso.angtowerdefense.op.OpReqJoin;
import me.funso.angtowerdefense.op.OpReqLogin;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketReader;
import me.funso.angtowerdefense.server.handler.AlertHandler;
import me.funso.angtowerdefense.server.handler.ReqJoinHandler;
import me.funso.angtowerdefense.server.handler.ReqLoginHandler;

public class SocketHandler extends Thread {
	Socket s;
	DataInputStream din;
	DataOutputStream dout;

	public SocketHandler(Socket s) throws IOException {
		this.s = s;
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
	}
	
	public void run() {
		try {
			while(true) {
				Packet packet = PacketReader.read(din);
				Op op = packet.readOp();
				Param param = new Param(s, din, dout);
				
				switch(op.getPacketOpcode()) {
				case ALERT: // fatal error, extra is UTF8Bytes
					AlertHandler.p(param, (OpAlert) op);
					break;
				case REQ_LOGIN:
					ReqLoginHandler.p(param, (OpReqLogin) op);
					break;
				case REQ_JOIN:
					ReqJoinHandler.p(param, (OpReqJoin) op);
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
					System.out.println("Not Implemented Opcode - " + op.getPacketOpcode().ordinal());
					break;
				
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		
		try {
			s.close();
			Server.session.remove(s);
		} catch (IOException e) {
		}
	}
}
