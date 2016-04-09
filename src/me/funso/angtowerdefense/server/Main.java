package me.funso.angtowerdefense.server;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
	public static void main(String args[]) {
		try {
			Server s = new Server(15349);
			s.serveForever();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
