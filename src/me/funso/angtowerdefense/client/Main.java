package me.funso.angtowerdefense.client;

import java.io.IOException;

import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.client.gui.MainMenu;

import javax.swing.*;

public class Main {
	
	public static Client c;
	public static User user;
	public static JFrame frame = new JFrame();
	
	public static void main(String args[]) throws InterruptedException {
		
		try {
			c = new Client("v.funso.me", 15349);
			//User user = c.login("user_id_test1", "12345678");
			//System.out.println(user);
			//c.join("user_id_test3", "123456", "nickname12345");
			//c.alert("test");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		/*try {
			if(c != null) {
				System.out.println("Closing connection in Main");
				c.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Device.dim.width, Device.dim.height);
		new MainMenu();
	}
}
