package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.client.Device;

public class MainMenu implements ActionListener {
	
	JFrame frame;
	private Container container;
	private JButton loginBtn;
	private JButton sign;
	private JButton option;
	private JLabel title;

	private Join join;
	private Login login;
	
	public MainMenu(JFrame frame) {
		this.frame = frame;
		this.container = frame.getContentPane();
		join = new Join(this);
		login = new Login(this);
		init();
	}
	
	public void init() {
		frame.setSize(Device.dim.width, Device.dim.height);
		container.setLayout(null);
		
		title = new JLabel("AngTowerDefense");
		title.setSize(Device.dim.width/100*65, Device.dim.height/3);
		title.setLocation(Device.dim.width/100*3, Device.dim.height/100*3);
		title.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		title.setHorizontalAlignment(JLabel.CENTER);
		frame.add(title);
		
		loginBtn = new JButton("LOGIN");
		loginBtn.setSize(Device.dim.width/5, Device.dim.height/10);
		loginBtn.setLocation(Device.dim.width/100*75, Device.dim.height/100*45);
		loginBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		loginBtn.addActionListener(this);
		frame.add(loginBtn);
		
		sign = new JButton("JOIN");
		sign.setSize(Device.dim.width/5, Device.dim.height/10);
		sign.setLocation(Device.dim.width/100*75, Device.dim.height/100*60);
		sign.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		sign.addActionListener(this);
		frame.add(sign);
		
		option = new JButton("OPTION");
		option.setSize(Device.dim.width/5, Device.dim.height/10);
		option.setLocation(Device.dim.width/100*75, Device.dim.height/100*75);
		option.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		option.addActionListener(this);
		frame.add(option);
		
		frame.setVisible(true);
	}
	
	public void resume() {
		frame.setContentPane(container);
		container.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		container.setVisible(false);
		if(e.getSource() == loginBtn) {
			login.resume();
		} else if(e.getSource() == sign) {
			join.resume(false);
		} else if(e.getSource() == option) {
		}
	}
}
