package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqJoin;
import me.funso.angtowerdefense.op.OpResJoin;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class Join {

	public static OpResJoin p(ClientParam param, String user_id, String user_pw, String nickname) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqJoin(user_id, user_pw, nickname));
		PacketWriter.write(param.dout, p);
		
		OpResJoin op = (OpResJoin)param.qs.get(PacketOpcode.RES_JOIN).take();
		
		/*if(op.errorCode != 0) {
			System.out.println(op.message);
		}*/
		
		return op;
	}

}
