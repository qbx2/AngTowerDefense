package me.funso.angtowerdefense.server.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Packet;
import me.funso.angtowerdefense.PacketOpcode;
import me.funso.angtowerdefense.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.SocketHandler.Param;

public class Login {
	
	public static void p(Param param) throws IOException, SQLException {
		Packet p = param.packet;
		DataOutputStream out = param.dout;
		
		String user_id = p.readUTF();
		String user_pw = p.readUTF();
		
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_user WHERE user_id=?"); // case insensitive
		ps.setString(1, user_id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			String salt = rs.getString("salt");
			if(rs.getString("user_pw").equals(SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")))) {
				// login ok
				System.out.println("Login Success");
				Packet p1 = new Packet();
				p1.writeOpcode(PacketOpcode.RESLOGIN);
				p1.writeUTF("Login Success");
				PacketWriter.write(out, p1);
				return;
			}
		}
		
		System.out.println("Login failure");
		Packet p1 = new Packet();
		p1.writeOpcode(PacketOpcode.RESLOGIN);
		p1.writeUTF("Login failure");
		PacketWriter.write(out, p1);
		// login fail
		//out.writeByte(fail.);
	}

}
