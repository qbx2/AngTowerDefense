package me.funso.angtowerdefense.server;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnector {
	private static class DBConfiguration {
		private static String url = null;
		private static String user = null;
		private static String password = null;
		private static Properties prop = null;
		
		private static String readProperty(String key) {
			if(prop == null) {
				prop = new Properties();
			}
			
			try {
				prop.load(new FileInputStream("dbconfig.properties"));
			} catch (IOException e) {
				prop = null;
				return null;
			}
			
			return prop.getProperty(key);
		}
		
		public static String getUrl() {
			if(url == null) {
				url = readProperty("url");
			}
			
			return url;
		}
		
		public static String getUser() {
			if(user == null) {
				user = readProperty("user");
			}
			
			return user;
		}
		
		public static String getPassword() {
			if(password == null) {
				password = readProperty("password");
			}
			
			return password;
		}
		
	}
	
	private static Connection conn = null;
	
	public static Connection getConnection() throws SQLException {
		if(conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(DBConfiguration.getUrl(), DBConfiguration.getUser(), DBConfiguration.getPassword());
			conn.setAutoCommit(true);
		}
		
		return conn;
	}
	
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		Connection conn = getConnection();
		return conn.prepareStatement(sql);
	}

	public static boolean checkConfiguration() throws SQLException {
		Connection conn = getConnection();
		ResultSet rs = conn.createStatement().executeQuery("SELECT VERSION();");
		if(rs.next()) {
			System.out.println("MySQL Version: " + rs.getString(1));
			return true;
		}
		
		return false;
	}
}
