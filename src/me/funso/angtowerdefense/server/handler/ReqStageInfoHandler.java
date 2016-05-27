package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.op.OpReqStageInfo;
import me.funso.angtowerdefense.op.OpResStageInfo;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;

public class ReqStageInfoHandler {
	public static void p(Param param, OpReqStageInfo op) throws IOException, SQLException {
		int idx = op.idx;
		System.out.println("ReqTowerInfoHandler("+idx+")");
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_stage WHERE idx=?");
		ps.setInt(1, idx);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			StageInfo info = new StageInfo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
			Packet p = new Packet();
			p.writeOp(new OpResStageInfo(info));
			PacketWriter.write(param.dout, p);
			return;
		} else {
			Packet p = new Packet();
			p.writeOp(new OpResStageInfo(null));
			PacketWriter.write(param.dout, p);
			return;
		}
	}
}
