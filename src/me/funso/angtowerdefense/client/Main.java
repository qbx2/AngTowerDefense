package me.funso.angtowerdefense.client;
import java.io.IOException;

public class Main {
	public static void main(String args[]) {
		Client c = null;
		
		try {
			c = new Client("localhost", 15349);
			c.login("user_id_test1", "12345679");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			if(c != null) {
				c.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
