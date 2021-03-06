package me.funso.angtowerdefense.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;

public class StageSelection implements ActionListener {
	
	Login prev;
	private Container container;
	private JLabel label;
	private JButton stageBtn[][];
	private JButton backBtn;
	private JLabel mainImage;

	final int ROW = 2;
	final int COLUMN = 5;
	int currentPage;
	
	public StageSelection(Login prev) {
		this.prev = prev;
		container = new Container();
		stageBtn = new JButton[ROW][COLUMN];
		currentPage = 0;
		init();
	}
	
	public void init() {

		Main.frame.setContentPane(container);
		
		label = new JLabel("SELECT STAGE");
		label.setSize(Device.dim.width/2, Device.dim.height/8);
		label.setLocation(Device.dim.width/4, Device.dim.height/25*2);
		label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/15));
		label.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(label);
		
		for(int i=0; i<ROW; i++)
			for(int j=0; j<COLUMN; j++) {
				stageBtn[i][j] = new JButton(""+(i*5+j+1));
				stageBtn[i][j].setSize(Device.dim.width/11, Device.dim.width/11);
				stageBtn[i][j].setLocation(Device.dim.width/11*(2*j+1), Device.dim.height/22*(5*i+6));
				stageBtn[i][j].setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
				stageBtn[i][j].setHorizontalAlignment(JButton.CENTER);
				Main.frame.add(stageBtn[i][j]);
				stageBtn[i][j].addActionListener(this);
			}

		backBtn = new JButton("MAIN");
		backBtn.setSize(Device.dim.width/6, Device.dim.height/8);
		backBtn.setLocation(Device.dim.width/36*27, Device.dim.height/12*9);
		backBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		backBtn.setHorizontalAlignment(JButton.CENTER);
		Main.frame.add(backBtn);
		backBtn.addActionListener(this);

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
	
	public void resume() {
		Main.frame.setContentPane(container);
		container.setVisible(true);
	}
	
	public void toMain() {
		prev.toMain(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backBtn) {
			container.setVisible(false);
			toMain();
		} else {
			for(int i=0; i<ROW; i++)
				for(int j=0; j<COLUMN; j++)
					if(e.getSource() == stageBtn[i][j]) {
						container.setVisible(false);
						new StageSelectResult(this, Integer.parseInt(stageBtn[i][j].getText()));
					}
		}
	}
}
