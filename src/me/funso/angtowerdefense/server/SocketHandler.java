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
import me.funso.angtowerdefense.op.OpReqLoadMap;
import me.funso.angtowerdefense.op.OpReqLogin;
import me.funso.angtowerdefense.op.OpReqMonsterInfo;
import me.funso.angtowerdefense.op.OpReqStageCount;
import me.funso.angtowerdefense.op.OpReqStageInfo;
import me.funso.angtowerdefense.op.OpReqTowerInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketReader;
import me.funso.angtowerdefense.server.handler.AlertHandler;
import me.funso.angtowerdefense.server.handler.ReqJoinHandler;
import me.funso.angtowerdefense.server.handler.ReqLoadMapHandler;
import me.funso.angtowerdefense.server.handler.ReqLoginHandler;
import me.funso.angtowerdefense.server.handler.ReqMonsterInfoHandler;
import me.funso.angtowerdefense.server.handler.ReqStageCountHandler;
import me.funso.angtowerdefense.server.handler.ReqStageInfoHandler;
import me.funso.angtowerdefense.server.handler.ReqTowerInfoHandler;

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
				case REQ_START_GAME:
					//startGame(packet);
					break;
				case REQ_SAVE_GAME:
					//saveGame(packet);
					break;
				case REQ_LOAD_GAME:
					//loadGame(packet);
					break;
				case REQ_LOAD_MAP:
					ReqLoadMapHandler.p(param, (OpReqLoadMap) op);
					break;
				case REQ_MONSTER_INFO:
					ReqMonsterInfoHandler.p(param, (OpReqMonsterInfo) op);
					break;
				case REQ_TOWER_INFO:
					ReqTowerInfoHandler.p(param, (OpReqTowerInfo) op);
				case REQ_STAGE_INFO:
					ReqStageInfoHandler.p(param, (OpReqStageInfo) op);
					break;
				case REQ_STAGE_COUNT:
					ReqStageCountHandler.p(param, (OpReqStageCount) op);
					break;
				case INSTALL_TOWER:
					//installTower(packet);
					break;
				case REQ_UPGRADE:
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
