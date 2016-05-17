package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.op.OpResLogin;

public class LoginResult implements ActionListener {
	Login prev;
	JFrame frame;
	private JLabel label;
	private JButton button;
	private Container container;
	private OpResLogin opResLogin;
	
	public LoginResult(OpResLogin opResLogin, Login prev) {
		this.prev = prev;
		frame = prev.frame;
		this.opResLogin = opResLogin;
		
		//
		opResLogin.errorCode = 0;
		//
		
		if(opResLogin.errorCode == 0) {
			Main.user = opResLogin.user;
			//
			Main.user = new User("sdsd", "nick", 20, 0, 0);
			//
			new StageSelection(prev);
		} else {
			container = new Container();
			init();
		}
	}
	
	public void init() {
		frame.setContentPane(container);
		
		label = new JLabel(opResLogin.message);
		label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		label.setSize(Device.dim.width/2, Device.dim.height/4);
		label.setLocation(Device.dim.width/4, Device.dim.height/25*4);
		label.setHorizontalAlignment(JLabel.CENTER);
		frame.add(label);

		button = new JButton("BACK");
		button.setSize(Device.dim.width/4, Device.dim.height/10);
		button.setLocation(Device.dim.width/8*3, Device.dim.height/25*13);
		button.setFont(new Font("궁서",Font.BOLD,Device.dim.height/15));
		button.setHorizontalAlignment(JLabel.CENTER);
		frame.add(button);
		button.addActionListener(this);
		
		frame.setVisible(true);
		container.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		prev.resume();
	}
}
