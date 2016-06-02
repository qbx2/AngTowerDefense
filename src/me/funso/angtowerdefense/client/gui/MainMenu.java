package me.funso.angtowerdefense.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;

public class MainMenu implements ActionListener {

	private Container container;
	private JButton loginBtn;
	private JButton sign;
	private JLabel title;
	private JLabel mainImage;
	private JLabel lcg;

	private Join join;
	private Login login;
	
	public MainMenu() {
		if(Main.frame != null) {
			container = Main.frame.getContentPane();
		}
		join = new Join(this);
		login = new Login(this);
		init();
	}
	
	public void init() {
		container.setLayout(null);

		title = new JLabel("AngTowerDefense");
		title.setSize(Device.dim.width/100*65, Device.dim.height/5);
		title.setLocation(Device.dim.width/100*3, 0);
		title.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
		title.setHorizontalAlignment(JLabel.CENTER);

		Main.frame.add(title);
		
		loginBtn = new JButton("LOGIN");
		loginBtn.setSize(Device.dim.width/5, Device.dim.height/10);
		loginBtn.setLocation(Device.dim.width/10*4, Device.dim.height/100*65);
		loginBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		loginBtn.addActionListener(this);
		Main.frame.add(loginBtn);
		
		sign = new JButton("JOIN");
		sign.setSize(Device.dim.width/5, Device.dim.height/10);
		sign.setLocation(Device.dim.width/10*4, Device.dim.height/100*80);
		sign.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		sign.addActionListener(this);
		Main.frame.add(sign);

		ImageIcon icon = new ImageIcon("main.jpeg");
		Image img = icon.getImage();
		img = img.getScaledInstance(Device.dim.width, Device.dim.height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		mainImage = new JLabel(icon);
		mainImage.setSize(Device.dim.width, Device.dim.height);
		mainImage.setLocation(0,0);
		mainImage.setHorizontalAlignment(JLabel.CENTER);

		icon = new ImageIcon("LCG.png");
		img = icon.getImage();
		img = img.getScaledInstance(Device.dim.width*245/800, Device.dim.width*300/800, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		lcg = new JLabel(icon);
		lcg.setSize(Device.dim.width*245/800, Device.dim.width*300/800);
		lcg.setLocation(Device.dim.width/100*68, Device.dim.height/100*30);
		lcg.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(lcg);
		Main.frame.add(mainImage);

		Main.frame.setVisible(true);
	}
	
	public void resume() {
		Main.frame.setContentPane(container);
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
		}
	}
}
