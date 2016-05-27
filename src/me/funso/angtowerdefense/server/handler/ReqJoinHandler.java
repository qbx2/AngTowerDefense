package me.funso.angtowerdefense.server.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.funso.angtowerdefense.Param;
import me.funso.angtowerdefense.op.OpReqJoin;
import me.funso.angtowerdefense.op.OpResJoin;
import me.funso.angtowerdefense.packet.PacketWriter;
import me.funso.angtowerdefense.server.MySQLConnector;
import me.funso.angtowerdefense.server.SHACalculator;
import me.funso.angtowerdefense.server.Server;

public class ReqJoinHandler {
	private static void myAssert(boolean cond, String message, Param param) throws ResultSentException, IOException {
		if(!cond) {
			PacketWriter.writeOp(param.dout, new OpResJoin(-1, message));
			throw new ResultSentException();
		}
	}

	private static boolean checkUserId(String user_id) throws SQLException {
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE user_id=?"); // case insensitive
		ps.setString(1, user_id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1) > 0;
	}

	private static boolean checkNickname(String nickname) throws SQLException {
		PreparedStatement ps = MySQLConnector.prepareStatement("SELECT count(*) FROM tbl_user WHERE nickname=?"); // case insensitive
		ps.setString(1, nickname);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1) > 0;
	}
	
	private static boolean join(String user_id, String salt, String user_pw, String nickname) throws SQLException, UnsupportedEncodingException {
		PreparedStatement ps = MySQLConnector.prepareStatement("INSERT INTO tbl_user (user_id, salt, user_pw, nickname) VALUES (?, ?, ?, ?)");
		ps.setString(1, user_id);
		ps.setString(2, salt);
		ps.setString(3, SHACalculator.calculate((salt + user_pw).getBytes("UTF-8")));
		ps.setString(4, nickname);
		
		return ps.executeUpdate() == 1;
	}
	
	public static void p(Param param, OpReqJoin op) throws IOException, SQLException, ResultSentException {
		String user_id = op.user_id;
		String user_pw = op.user_pw;
		String nickname = op.nickname;
		
		myAssert(user_id.length() >= 4, "user_id must be longer than or equal to 4.", param);
		myAssert(user_pw.length() >= 4, "user_pw must be longer than or equal to 4.", param);
		myAssert(nickname.length() >= 2, "nickname must be longer than or equal to 2.", param);
		myAssert(!Server.session.containsKey(param.sock), "You're already logged in.", param);
		myAssert(!checkUserId(user_id), "user_id '"+user_id+"' is already in use.", param);
		myAssert(!checkNickname(nickname), "nickname '"+nickname+"' is already in use.", param);
	
		String salt = SHACalculator.generateSalt(32);
		
		if(join(user_id, salt, user_pw, nickname)) {
			//success
			//System.out.println("Join Success");
			PacketWriter.writeOp(param.dout, new OpResJoin(0, null));
		} else {
			PacketWriter.writeOp(param.dout, new OpResJoin(-1, "Join has failed by unknown error."));
		}
	}
}
