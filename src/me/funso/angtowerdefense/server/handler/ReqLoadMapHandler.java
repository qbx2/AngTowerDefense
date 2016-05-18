package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.MapParser;
import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.Tile;
import me.funso.angtowerdefense.client.astar.Map;
import me.funso.angtowerdefense.op.OpReqLoadMap;
import me.funso.angtowerdefense.op.OpResJoin;
import me.funso.angtowerdefense.op.OpResLoadMap;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;

public class ReqLoadMapHandler {
	public static void p(Param param, OpReqLoadMap op) throws IOException, SQLException {
		int idx = op.idx;
		System.out.println("ReqLoadMapHandler("+idx+")");
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT data FROM tbl_map WHERE idx=?");
		ps.setInt(1, idx);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			String data = rs.getString(1);
			Packet p = new Packet();
			p.writeOp(new OpResLoadMap(data));
			PacketWriter.write(param.dout, p);
			return;
		} else {
			Packet p = new Packet();
			p.writeOp(new OpResLoadMap(null));
			PacketWriter.write(param.dout, p);
			return;
		}
	}
}
