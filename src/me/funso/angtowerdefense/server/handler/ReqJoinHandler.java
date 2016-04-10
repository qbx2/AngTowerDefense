package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqJoin;
import me.funso.angtowerdefense.op.OpResJoin;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.Server;

public class ReqJoinHandler {
	public static void p(Param param, OpReqJoin op) throws IOException, SQLException {
		String user_id = op.user_id;
		String user_pw = op.user_pw;
		String nickname = op.nickname;
		
		if(user_id.length() < 4) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(1, "user_id must be longer than or equal to 4."));
			PacketWriter.write(param.dout, p);
			return;
		} else if(user_pw.length() < 4) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(2, "user_pw must be longer than or equal to 4."));
			PacketWriter.write(param.dout, p);
			return;
		} else if(nickname.length() < 2) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(3, "nickname must be longer than or equal to 2."));
			PacketWriter.write(param.dout, p);
			return;
		} else if(Server.session.containsKey(param.sock)) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(6, "You're already logged in."));
			PacketWriter.write(param.dout, p);
			return;
		}
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE user_id=?"); // case insensitive
		ps.setString(1, user_id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		if(rs.getInt(1) > 0) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(4, "user_id '"+user_id+"' is already in use."));
			PacketWriter.write(param.dout, p);
			return;		
		}
		
		ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE nickname=?"); // case insensitive
		ps.setString(1, nickname);
		rs = ps.executeQuery();
		rs.next();
		
		if(rs.getInt(1) > 0) {
			Packet p = new Packet();
			p.writeOp(new OpResJoin(5, "nickname '"+nickname+"' is already in use."));
			PacketWriter.write(param.dout, p);
			return;		
			
		}
	
		String salt = SHACalculator.generateSalt(32);
		
		ps = MySQLConnector.prepareStatement("INSERT INTO tbl_user (user_id, salt, user_pw, nickname) VALUES (?, ?, ?, ?)");
		ps.setString(1, user_id);
		ps.setString(2, salt);
		ps.setString(3, SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")));
		ps.setString(4, nickname);
		
		if(ps.executeUpdate() == 1) {
			//success
			System.out.println("Join Success");
			
			Packet p = new Packet();
			p.writeOp(new OpResJoin(0, null));
			PacketWriter.write(param.dout, p);
			return;
		}	
		
		Packet p = new Packet();
		p.writeOp(new OpResJoin(-1, "Join has failed by unknown error."));
		PacketWriter.write(param.dout, p);
	}
}
