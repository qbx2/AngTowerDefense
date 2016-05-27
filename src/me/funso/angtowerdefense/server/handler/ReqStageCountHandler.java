package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqStageCount;
import me.funso.angtowerdefense.op.OpResStageCount;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;

public class ReqStageCountHandler {
	public static void p(Param param, OpReqStageCount op) throws IOException, SQLException {
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT COUNT(*) FROM tbl_stage");
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			Packet p = new Packet();
			p.writeOp(new OpResStageCount(rs.getInt(1)));
			PacketWriter.write(param.dout, p);
			return;
		} else {
			Packet p = new Packet();
			p.writeOp(new OpResStageCount(-1));
			PacketWriter.write(param.dout, p);
			return;
		}
	}
}
