package me.funso.angtowerdefense.client;
import java.io.IOException;

import me.funso.angtowerdefense.User;

public class Main {
	public static void main(String args[]) {
		Client c = null;
		
		try {
			c = new Client("localhost", 15349);
			User user = c.login("user_id_test1", "12345678");
			System.out.println(user);
			//c.join("user_id_test3", "123456", "nickname12345");
			//c.alert("test");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			if(c != null) {
				System.out.println("Closing connection in Main");
				c.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
