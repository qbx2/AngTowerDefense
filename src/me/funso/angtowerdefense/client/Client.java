package me.funso.angtowerdefense.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import me.funso.angtowerdefense.client.api.Alert;
import me.funso.angtowerdefense.client.api.Join;
import me.funso.angtowerdefense.client.api.LoadMap;
import me.funso.angtowerdefense.client.api.Login;
import me.funso.angtowerdefense.client.api.MonsterInfo;
import me.funso.angtowerdefense.client.api.StageCount;
import me.funso.angtowerdefense.client.api.StageInfo;
import me.funso.angtowerdefense.client.api.TowerInfo;
import me.funso.angtowerdefense.client.handler.AlertHandler;
import me.funso.angtowerdefense.op.Op;
import me.funso.angtowerdefense.op.OpResJoin;
import me.funso.angtowerdefense.op.OpResLoadMap;
import me.funso.angtowerdefense.op.OpResLogin;
import me.funso.angtowerdefense.op.OpResMonsterInfo;
import me.funso.angtowerdefense.op.OpResStageCount;
import me.funso.angtowerdefense.op.OpResStageInfo;
import me.funso.angtowerdefense.op.OpResTowerInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketReader;

public class Client {
	private Socket s = null;
	protected DataOutputStream dout = null;
	protected DataInputStream din = null;
	protected PacketReceiver pr = null;
	private ClientParam param = null; 
	
	private class PacketReceiver extends Thread {
		public HashMap<PacketOpcode, LinkedBlockingQueue<Op>> qs;
		
		public PacketReceiver() {
			qs = new HashMap<PacketOpcode, LinkedBlockingQueue<Op>>();
			
			for(PacketOpcode op: PacketOpcode.values()) {
				qs.put(op, new LinkedBlockingQueue<Op>());
			}
		}
		
		public void run() {			
			try {
				while(true) {
					Packet p = PacketReader.read(din);
					Op op = p.readOp();
					LinkedBlockingQueue<Op> q = qs.get(op.getPacketOpcode());
					q.put(op);
				}
			} catch (IOException | InterruptedException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Client(String hostname, int port) throws UnknownHostException, IOException {
		pr = new PacketReceiver();
		s = new Socket(hostname, port);
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		param = new ClientParam(s, din, dout, pr.qs);
		
		pr.start();
		new AlertHandler(param).start();
	}
	
	public void alert(String message) throws IOException {
		Alert.p(param , message);
	}

	public OpResLogin login(String user_id, String user_pw) throws IOException, InterruptedException {
		return Login.p(param, user_id, user_pw);
	}

	public OpResJoin join(String user_id, String user_pw, String nickname) throws IOException, InterruptedException {
		return Join.p(param, user_id, user_pw, nickname);
	}

	public OpResMonsterInfo monsterInfo(int idx) throws IOException, InterruptedException {
		return MonsterInfo.p(param, idx);
	}

	public OpResTowerInfo towerInfo(int idx) throws IOException, InterruptedException {
		return TowerInfo.p(param, idx);
	}

	public OpResStageCount stageCount() throws IOException, InterruptedException {
		return StageCount.p(param);
	}

	public OpResStageInfo stageInfo(int idx) throws IOException, InterruptedException {
		return StageInfo.p(param, idx);
	}

	public OpResLoadMap loadMap(int idx) throws IOException, InterruptedException {
		return LoadMap.p(param, idx);
	}

	public void close() throws IOException {
		s.close();
	}
}
