package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqStageInfo;
import me.funso.angtowerdefense.op.OpResStageInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class StageInfo {

	public static OpResStageInfo p(ClientParam param, int idx) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqStageInfo(idx));
		PacketWriter.write(param.dout, p);

		OpResStageInfo op = (OpResStageInfo)param.qs.get(PacketOpcode.RES_STAGE_INFO).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}
