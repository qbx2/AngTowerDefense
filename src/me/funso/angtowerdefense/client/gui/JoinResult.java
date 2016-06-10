package me.funso.angtowerdefense.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.op.OpResJoin;

public class JoinResult implements ActionListener {
	Join prev;
	private JLabel label;
	private JButton button;
	private Container container;
	private OpResJoin opResJoin;
	private JLabel mainImage;
	
	public JoinResult(OpResJoin opResJoin, Join prev) {
		this.prev = prev;
		container = new Container();
		this.opResJoin = opResJoin;
		init();
	}
	
	public void init() {
		Main.frame.setContentPane(container);
		
		if(opResJoin.errorCode == 0) {
			label = new JLabel("JOIN SUCCESS");
			label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
			button = new JButton("CONFIRM");
		}
		else {
			label = new JLabel(opResJoin.message);
			label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
			button = new JButton("BACK");
		}
		label.setSize(Device.dim.width/2, Device.dim.height/4);
		label.setLocation(Device.dim.width/4, Device.dim.height/25*4);
		label.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(label);
		
		button.setSize(Device.dim.width/4, Device.dim.height/10);
		button.setLocation(Device.dim.width/8*3, Device.dim.height/25*13);
		button.setFont(new Font("궁서",Font.BOLD,Device.dim.height/15));
		button.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(button);
		button.addActionListener(this);

		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/main.jpeg"));
		Image img = icon.getImage();
		img = img.getScaledInstance(Device.dim.width, Device.dim.height, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		mainImage = new JLabel(icon);
		mainImage.setSize(Device.dim.width, Device.dim.height);
		mainImage.setLocation(0,0);
		mainImage.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(mainImage);

		Main.frame.setVisible(true);
		container.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		container.setVisible(false);
		if(opResJoin.errorCode == 0)
			prev.toMain();
		else
			prev.resume(true);
	}
}
