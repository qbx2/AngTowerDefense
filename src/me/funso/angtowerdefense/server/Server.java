package me.funso.angtowerdefense.server;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.io.IOException;

public class Server {
	ServerSocket ss;
	
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
