package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.op.OpResLogin;

public class Login implements ActionListener {
	
	MainMenu prev;
	JFrame frame;
	private Container container;
	private JLabel idLabel, pwdLabel, login;
	private JTextField idText;
	private JButton ok, back;
	private JPasswordField pwd;
	
	public Login(MainMenu prev) {
		this.prev = prev;
		frame = prev.frame;
		container = new Container();
		init();
	}
	
	public void init() {
		frame.setContentPane(container);
		
		login = new JLabel("LOGIN");
		login.setSize(Device.dim.width/2, Device.dim.height/10);
		login.setLocation(Device.dim.width/4, Device.dim.height/25*3);
		login.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
		login.setHorizontalAlignment(JLabel.CENTER);
		frame.add(login);
		
		idLabel = new JLabel("ID");
		idLabel.setSize(Device.dim.width/7, Device.dim.height/10);
		idLabel.setLocation(Device.dim.width/16*4, Device.dim.height/25*8);
		idLabel.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		idLabel.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(idLabel);
		
		idText = new JTextField(20);
		idText.setSize(Device.dim.width/4, Device.dim.height/10);
		idText.setLocation(Device.dim.width/32*15, Device.dim.height/25*8);
		idText.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		idText.setHorizontalAlignment(JTextField.CENTER);
		frame.add(idText);
		idText.addActionListener(this);
		
		pwdLabel = new JLabel("PW");
		pwdLabel.setSize(Device.dim.width/7, Device.dim.height/10);
		pwdLabel.setLocation(Device.dim.width/16*4, Device.dim.height/25*11);
		pwdLabel.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		pwdLabel.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(pwdLabel);
		
		pwd = new JPasswordField(20);
		pwd.setEchoChar('*');
		pwd.setSize(Device.dim.width/4, Device.dim.height/10);
		pwd.setLocation(Device.dim.width/32*15, Device.dim.height/25*11);
		pwd.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		pwd.setHorizontalAlignment(JPasswordField.CENTER);
		frame.add(pwd);
		pwd.addActionListener(this);
		
		ok = new JButton("LOGIN");
		ok.setSize(Device.dim.width/6, Device.dim.height/15);
		ok.setLocation(Device.dim.width/12*5, Device.dim.height/50*31);
		ok.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		frame.add(ok);
		ok.addActionListener(this);
		
		back = new JButton("BACK");
		back.setSize(Device.dim.width/6, Device.dim.height/15);
		back.setLocation(Device.dim.width/4*3, Device.dim.height/5*4);
		back.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		frame.add(back);
		back.addActionListener(this);
		
		toMain(false);
	}
	
	public void resume() {
		frame.setContentPane(container);
		container.setVisible(true);
	}
	
	public void toMain(boolean isLogout) {
		if(isLogout) {
			idText.setText("");
			pwd.setText("");
		}
		prev.resume();
	}
	
	public void loginResult(OpResLogin opResLogin) {
		new LoginResult(opResLogin, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		container.setVisible(false);
		if(e.getSource() == back) {
			toMain(true);
		} else if(e.getSource() == ok || e.getSource() == pwd) {
			try {
				loginResult(Main.c.login(idText.getText(), pwd.getText()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				loginResult(new OpResLogin(-1, "CONNECTION CUT OFF", null));
			}
		}
	}
}
