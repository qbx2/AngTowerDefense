package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqMonsterInfo;
import me.funso.angtowerdefense.op.OpResMonsterInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class MonsterInfo {

	public static OpResMonsterInfo p(ClientParam param, int idx) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqMonsterInfo(idx));
		PacketWriter.write(param.dout, p);
		
		OpResMonsterInfo op = (OpResMonsterInfo)param.qs.get(PacketOpcode.RES_MONSTER_INFO).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}
