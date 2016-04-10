package me.funso.angtowerdefense.client.api;

import java.io.IOException;

import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.client.ClientParam;
import me.funso.angtowerdefense.op.OpReqLogin;
import me.funso.angtowerdefense.op.OpResLogin;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketOpcode;
import me.funso.angtowerdefense.packet.PacketWriter;

public class Login {

	public static User p(ClientParam param, String user_id, String user_pw) throws IOException, InterruptedException {
		Packet p = new Packet();
		p.writeOp(new OpReqLogin(user_id, user_pw));
		PacketWriter.write(param.dout, p);
		
		OpResLogin op = (OpResLogin)param.qs.get(PacketOpcode.RES_LOGIN).take();
		
		if(op.errorCode != 0) {
			System.out.println(op.message);
		}
		
		return op.user;
	}

}
