package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqMonsterInfo;
import me.funso.angtowerdefense.op.OpResMonsterInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;

public class ReqMonsterInfoHandler {
	public static void p(Param param, OpReqMonsterInfo op) throws IOException, SQLException {
		int idx = op.idx;
		System.out.println("ReqMonsterInfoHandler("+idx+")");
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_monster_info WHERE idx=?");
		ps.setInt(1, idx);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			MonsterInfo info = new MonsterInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
			Packet p = new Packet();
			p.writeOp(new OpResMonsterInfo(info));
			PacketWriter.write(param.dout, p);
			return;
		} else {
			Packet p = new Packet();
			p.writeOp(new OpResMonsterInfo(null));
			PacketWriter.write(param.dout, p);
			return;
		}
	}
}
