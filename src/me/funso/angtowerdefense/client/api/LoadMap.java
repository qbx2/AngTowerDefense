package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqLoadMap;
import me.funso.angtowerdefense.op.OpResLoadMap;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class LoadMap {

	public static OpResLoadMap p(ClientParam param, int idx) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqLoadMap(idx));
		PacketWriter.write(param.dout, p);
		
		OpResLoadMap op = (OpResLoadMap)param.qs.get(PacketOpcode.RES_LOAD_MAP).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}