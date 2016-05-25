package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqTowerInfo;
import me.funso.angtowerdefense.op.OpResTowerInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class TowerInfo {

	public static OpResTowerInfo p(ClientParam param, int idx) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqTowerInfo(idx));
		PacketWriter.write(param.dout, p);
		
		OpResTowerInfo op = (OpResTowerInfo)param.qs.get(PacketOpcode.RES_TOWER_INFO).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}
