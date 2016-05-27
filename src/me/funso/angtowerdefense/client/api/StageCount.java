package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqStageCount;
import me.funso.angtowerdefense.op.OpResStageCount;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class StageCount {

	public static OpResStageCount p(ClientParam param) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqStageCount());
		PacketWriter.write(param.dout, p);
		
		OpResStageCount op = (OpResStageCount)param.qs.get(PacketOpcode.RES_STAGE_COUNT).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}
