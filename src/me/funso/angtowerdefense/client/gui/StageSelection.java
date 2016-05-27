package me.funso.angtowerdefense.client.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;

public class StageSelection implements ActionListener {
	
	Login prev;
	private Container container;
	private JLabel label;
	private JButton stageBtn[][];
	private JButton nextBtn,prevBtn,backBtn,skillBtn;
	
	final int MAX_PAGE = 2;
	final int ROW = 2;
	final int COLUMN = 5;
	int currentPage;

	public static StageInfo[] stageInfo;
	
	public StageSelection(Login prev) {
		this.prev = prev;
		container = new Container();
		stageBtn = new JButton[ROW][COLUMN];
		currentPage = 0;
		init();
	}
	
	public void init() {
		try {
			stageInfo = new StageInfo[Main.c.stageCount().count];
			for (int i = 0; i < stageInfo.length; i++) {
				stageInfo[i] = Main.c.stageInfo(i + 1).info;
			}
		} catch(Exception e) {}

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

		prevBtn = new JButton("PREV");
		prevBtn.setSize(Device.dim.width/6, Device.dim.height/8);
		prevBtn.setLocation(Device.dim.width/36*11, Device.dim.height/12*9);
		prevBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		prevBtn.setHorizontalAlignment(JButton.CENTER);
		Main.frame.add(prevBtn);
		prevBtn.addActionListener(this);

		nextBtn = new JButton("NEXT");
		nextBtn.setSize(Device.dim.width/6, Device.dim.height/8);
		nextBtn.setLocation(Device.dim.width/36*19, Device.dim.height/12*9);
		nextBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		nextBtn.setHorizontalAlignment(JButton.CENTER);
		Main.frame.add(nextBtn);
		nextBtn.addActionListener(this);

		backBtn = new JButton("MAIN");
		backBtn.setSize(Device.dim.width/6, Device.dim.height/8);
		backBtn.setLocation(Device.dim.width/36*27, Device.dim.height/12*9);
		backBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		backBtn.setHorizontalAlignment(JButton.CENTER);
		Main.frame.add(backBtn);
		backBtn.addActionListener(this);

		skillBtn = new JButton("SKILL");
		skillBtn.setSize(Device.dim.width/6, Device.dim.height/8);
		skillBtn.setLocation(Device.dim.width/36*3, Device.dim.height/12*9);
		skillBtn.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
		skillBtn.setHorizontalAlignment(JButton.CENTER);
		Main.frame.add(skillBtn);
		skillBtn.addActionListener(this);

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
		if(e.getSource() == nextBtn) {
			currentPage = (currentPage+1)%MAX_PAGE;
			for(int i=0; i<ROW; i++)
				for(int j=0; j<COLUMN; j++)
					stageBtn[i][j].setText(""+(currentPage*10+i*5+j+1));
		} else if(e.getSource() == prevBtn) {
			currentPage--;
			if(currentPage<0)
				currentPage = MAX_PAGE-1;
			for(int i=0; i<ROW; i++)
				for(int j=0; j<COLUMN; j++)
					stageBtn[i][j].setText(""+(currentPage*10+i*5+j+1));
		} else if(e.getSource() == backBtn) {
			container.setVisible(false);
			toMain();
		} else if(e.getSource() == skillBtn) {
			container.setVisible(false);
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
