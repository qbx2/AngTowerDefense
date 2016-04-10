package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.op.OpReqLogin;
import me.funso.angtowerdefense.op.OpResLogin;
import me.funso.angtowerdefense.packet.Packet;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.Server;

public class ReqLoginHandler {
	
	public static void p(Param param, OpReqLogin op) throws IOException, SQLException {	
		String user_id = op.user_id;
		String user_pw = op.user_pw;
		
		if(user_id.length() < 4) {
			Packet p = new Packet();
			p.writeOp(new OpResLogin(1, "user_id must be longer than or equal to 4.", null));
			PacketWriter.write(param.dout, p);
			return;
		} else if(user_pw.length() < 4) {
			Packet p = new Packet();
			p.writeOp(new OpResLogin(2, "user_pw must be longer than or equal to 4.", null));
			PacketWriter.write(param.dout, p);
			return;
		}
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_user WHERE user_id=?"); // case insensitive
		ps.setString(1, user_id);
		ResultSet rs = ps.executeQuery();
		
		if(!rs.next()) {
			System.out.println("Login failed.");
			Packet p = new Packet();
			p.writeOp(new OpResLogin(3, "Login failed.", null));
			PacketWriter.write(param.dout, p);
			return;
		}
		
		String salt = rs.getString("salt");
		if(rs.getString("user_pw").equals(SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")))) {
			// login ok
			User user = new User(rs.getString("user_id"), rs.getString("nickname"), rs.getInt("level"), rs.getInt("exp"), rs.getInt("gold"));
			
			Server.session.put(param.sock, user);
			System.out.println("Login Success");
			System.out.println(user);
			
			Packet p = new Packet();
			p.writeOp(new OpResLogin(0, null, user));
			PacketWriter.write(param.dout, p);			
			return;
		}
		
		System.out.println("Login failed.");
		Packet p = new Packet();
		p.writeOp(new OpResLogin(4, "Login failed.", null));
		PacketWriter.write(param.dout, p);
	}

}
