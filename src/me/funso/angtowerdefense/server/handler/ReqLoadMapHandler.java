package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqLoadMap;
import me.funso.angtowerdefense.op.OpResLoadMap;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;

public class ReqLoadMapHandler {
	public static void p(Param param, OpReqLoadMap op) throws IOException, SQLException {
		int idx = op.idx;
		System.out.println("ReqLoadMapHandler("+idx+")");
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT data FROM tbl_map WHERE idx=?");
		ps.setInt(1, idx);
		ResultSet rs = ps.executeQuery();
		PacketWriter.writeOp(param.dout, new OpResLoadMap(rs.next() ? rs.getString(1) : null));
	}
}
