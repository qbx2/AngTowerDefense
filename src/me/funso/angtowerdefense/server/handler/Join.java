package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Packet;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.SocketHandler.Param;

public class Join {
	public static void p(Param param) throws IOException, SQLException {
		Packet p = param.packet;
		String user_id = p.readUTF();
		String user_pw = p.readUTF();
		String nickname = p.readUTF();
		
		if(user_id.length() >= 4 && user_pw.length() >= 4 && nickname.length() >= 2) {
			PreparedStatement ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE user_id=?"); // case insensitive
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			if(rs.getInt(1) == 0) {		
				ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE nickname=?"); // case insensitive
				ps.setString(1, nickname);
				rs = ps.executeQuery();
				rs.next();
				
				if(rs.getInt(1) == 0) {
					String salt = SHACalculator.generateSalt(32);
					
					ps = MySQLConnector.prepareStatement("INSERT INTO tbl_user (user_id, salt, user_pw, nickname) VALUES (?, ?, ?, ?)");
					ps.setString(1, user_id);
					ps.setString(2, salt);
					ps.setString(3, SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")));
					ps.setString(4, nickname);
					if(ps.executeUpdate() == 1) {
						//success
						System.out.println("Join success");
						return;
					}
				}
			}
		}
		
		System.out.println("Join failure");
	}
}
