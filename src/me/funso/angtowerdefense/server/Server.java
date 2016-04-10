package me.funso.angtowerdefense.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

import me.funso.angtowerdefense.User;

public class Server {
	private ServerSocket ss;
	public static HashMap<Socket, User> session = new HashMap<Socket, User>();
	
	public Server(int port) throws IOException, SQLException {
		this.ss = new ServerSocket(port);
		System.out.println("The server is running at :"+port);
		MySQLConnector.checkConfiguration();
	}

	public void serveForever() throws IOException {
		try {
			while(true) {
				Socket s = ss.accept();
				new SocketHandler(s).start();
			}
		} finally {
			ss.close();
		}
	}
}
