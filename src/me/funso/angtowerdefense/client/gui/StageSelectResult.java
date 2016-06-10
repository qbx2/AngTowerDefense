package me.funso.angtowerdefense.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.GameMain;

public class StageSelectResult implements ActionListener {
	
	StageSelection prev;
	private JLabel label;
	private JButton button;
	private Container container;
	private JLabel mainImage;
	
	public StageSelectResult(StageSelection prev, int level) {
		if(Main.user.level >= level) {
			try {
				new GameMain(prev, level);
			} catch(IOException | InterruptedException e) {
				this.prev = prev;
				container = new Container();
				label = new JLabel("Exception Threw");
				init();
			}
		} else {
			this.prev = prev;
			container = new Container();
			label = new JLabel("LEVEL LIMIT");
			init();
		}
	}
	
	public void init() {
		Main.frame.setContentPane(container);
		
		label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		label.setSize(Device.dim.width/2, Device.dim.height/4);
		label.setLocation(Device.dim.width/4, Device.dim.height/25*4);
		label.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(label);

		button = new JButton("BACK");
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
		prev.resume();
	}
}
