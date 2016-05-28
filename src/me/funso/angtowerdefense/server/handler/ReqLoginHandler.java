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
	private static class UserData {
		String user_id;
		String salt;
		String user_pw;
		String nickname;
		int level;
		int exp;
		int gold;

		public UserData(String user_id, String salt, String user_pw, String nickname, int level, int exp, int gold) {
			this.user_id = user_id;
			this.salt = salt;
			this.user_pw = user_pw;
			this.nickname = nickname;
			this.level = level;
			this.exp = exp;
			this.gold = gold;
		}
	}

	private static void myAssert(boolean cond, String message, Param param) throws ResultSentException, IOException {
		if(!cond) {
			PacketWriter.writeOp(param.dout, new OpResLogin(-1, message, null));
			throw new ResultSentException();
		}
	}

	private static UserData getUser(String user_id) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = MySQLConnector.prepareStatement("SELECT * FROM tbl_user WHERE user_id=?"); // case insensitive
			ps.setString(1, user_id);
			rs = ps.executeQuery();
			return rs.next() ? new UserData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)) : null;
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
		
		UserData userData = getUser(user_id);
		myAssert(userData != null, "Login failed", param);
	
		if(checkPassword(userData.user_pw, userData.salt, user_pw)) {
			// login ok
			User user = new User(userData.user_id, userData.nickname, userData.level, userData.exp, userData.gold);
			
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
