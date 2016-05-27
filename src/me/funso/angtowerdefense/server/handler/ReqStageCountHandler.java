package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqStageCount;
import me.funso.angtowerdefense.op.OpResStageCount;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;

public class ReqStageCountHandler {
	public static void p(Param param, OpReqStageCount op) throws IOException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = MySQLConnector.prepareStatement("SELECT COUNT(*) FROM tbl_stage");
			rs = ps.executeQuery();
			PacketWriter.writeOp(param.dout, new OpResStageCount(rs.next() ? rs.getInt(1) : -1));
		} finally {
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
		}
	}
}
