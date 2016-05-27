package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.op.OpReqLogin;
import me.funso.angtowerdefense.op.OpResLogin;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.Server;

public class ReqLoginHandler {
	private static void myAssert(boolean cond, String message, Param param) throws ResultSentException, IOException {
		if(!cond) {
			PacketWriter.writeOp(param.dout, new OpResLogin(-1, message, null));
			throw new ResultSentException();
		}
	}

	private static ResultSet getUser(String user_id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_user WHERE user_id=?"); // case insensitive
			ps.setString(1, user_id);
			rs = ps.executeQuery();
			return rs;
		} finally {
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
		}
	}

	private static boolean checkPassword(String encrypted_user_pw, String salt, String user_pw) throws UnsupportedEncodingException {
		return encrypted_user_pw.equals(SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")));
	}
	
	public static void p(Param param, OpReqLogin op) throws IOException, SQLException, ResultSentException {	
		String user_id = op.user_id;
		String user_pw = op.user_pw;
		
		myAssert(user_id.length() >= 4, "user_id must be longer than or equal to 4.", param);
		myAssert(user_pw.length() >= 4, "user_pw must be longer than or equal to 4.", param);
		myAssert(!Server.session.containsKey(param.sock), "You're already logged in.", param);
		
		ResultSet rs = getUser(user_id);
		myAssert(rs.next(), "Login failed", param);
		
		String encrypted_user_pw = rs.getString("user_pw");
		String salt = rs.getString("salt");
		
		if(checkPassword(encrypted_user_pw, salt, user_pw)) {
			// login ok
			User user = new User(rs.getString("user_id"), rs.getString("nickname"), rs.getInt("level"), rs.getInt("exp"), rs.getInt("gold"));
			
			// set session
			Server.session.put(param.sock, user);
			//System.out.println("Login Success");
			//System.out.println(user);
			
			PacketWriter.writeOp(param.dout, new OpResLogin(0, null, user));
		} else {
			PacketWriter.writeOp(param.dout, new OpResLogin(-1, "Login failed", null));
		}
	}

}
