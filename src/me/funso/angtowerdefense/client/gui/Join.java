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
import me.funso.angtowerdefense.op.OpResJoin;

public class Join implements ActionListener {
	
	MainMenu prev;
	JFrame frame;
	private Container container;
	private JLabel idLabel, pwdLabel, signIn, nickname;
	private JTextField idText, nickText;
	private JButton ok, back;
	private JPasswordField pwd;
	
	public Join(MainMenu prev) {
		this.prev = prev;
		frame = prev.frame;
		container = new Container();
		init();
	}
	
	public void init() {
		frame.setContentPane(container);
		
		signIn = new JLabel("JOIN");
		signIn.setSize(Device.dim.width/2, Device.dim.height/9);
		signIn.setLocation(Device.dim.width/4, Device.dim.height/25*3);
		signIn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
		signIn.setHorizontalAlignment(JLabel.CENTER);
		frame.add(signIn);
		
		idLabel = new JLabel("ID");
		idLabel.setSize(Device.dim.width/7, Device.dim.height/10);
		idLabel.setLocation(Device.dim.width/16*4, Device.dim.height/50*15);
		idLabel.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		idLabel.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(idLabel);
		
		idText = new JTextField(20);
		idText.setSize(Device.dim.width/4, Device.dim.height/10);
		idText.setLocation(Device.dim.width/32*15, Device.dim.height/50*15);
		idText.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		idText.setHorizontalAlignment(JTextField.CENTER);
		frame.add(idText);
		
		nickname = new JLabel("NICK");
		nickname.setSize(Device.dim.width/7, Device.dim.height/10);
		nickname.setLocation(Device.dim.width/16*4, Device.dim.height/50*21);
		nickname.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		nickname.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(nickname);

		nickText = new JTextField(20);
		nickText.setSize(Device.dim.width/4, Device.dim.height/10);
		nickText.setLocation(Device.dim.width/32*15, Device.dim.height/50*21);
		nickText.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		nickText.setHorizontalAlignment(JTextField.CENTER);
		frame.add(nickText);
		
		pwdLabel = new JLabel("PW");
		pwdLabel.setSize(Device.dim.width/7, Device.dim.height/10);
		pwdLabel.setLocation(Device.dim.width/16*4, Device.dim.height/50*27);
		pwdLabel.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		pwdLabel.setHorizontalAlignment(JLabel.RIGHT);
		frame.add(pwdLabel);
		
		pwd = new JPasswordField(20);
		pwd.setEchoChar('*');
		pwd.setSize(Device.dim.width/4, Device.dim.height/10);
		pwd.setLocation(Device.dim.width/32*15, Device.dim.height/50*27);
		pwd.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		pwd.setHorizontalAlignment(JPasswordField.CENTER);
		frame.add(pwd);
		pwd.addActionListener(this);
		
		ok = new JButton("SUBMIT");
		ok.setSize(Device.dim.width/6, Device.dim.height/15);
		ok.setLocation(Device.dim.width/12*5, Device.dim.height/50*35);
		ok.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		frame.add(ok);
		ok.addActionListener(this);
		
		back = new JButton("BACK");
		back.setSize(Device.dim.width/6, Device.dim.height/15);
		back.setLocation(Device.dim.width/4*3, Device.dim.height/5*4);
		back.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		frame.add(back);
		back.addActionListener(this);
		
		toMain();
	}
	
	public void resume(boolean isBack) {
		frame.setContentPane(container);
		if(!isBack) {
			idText.setText("");
			pwd.setText("");
			nickText.setText("");
		}
		container.setVisible(true);
	}
	
	public void toMain() {
		prev.resume();
	}
	
	public void joinResult(OpResJoin opResJoin) {
		new JoinResult(opResJoin, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		container.setVisible(false);
		if(e.getSource() == ok || e.getSource() == pwd || e.getSource() == idText) {
			try {
				joinResult(Main.c.join(idText.getText(), pwd.getText(), nickText.getText()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				joinResult(new OpResJoin(-1, "CONNECTION CUT OFF"));
			}
		} else if(e.getSource() == back) {
			toMain();
		}
	}
}
