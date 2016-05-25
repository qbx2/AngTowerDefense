package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.Game;
import me.funso.angtowerdefense.Tower;
import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.timer.DrawTimer;

public class GameMain extends Container implements ActionListener, MouseListener {
	
	final int TOWER_NUM = 10;
	
	JFrame frame;
	private StageSelection prev;
	private JButton btn[];
	
	private JButton[] towerBuyBtn;
	public static TowerInfo[] towerInfo;
	
	private Graphics g;
	
	private Game map;
	
	private int mineral;
	private int life;
	public static int level;
	
	private int selected;
	public static int game_speed;
	
	DrawTimer drawTimer;
    Timer jobScheduler;
	
	public GameMain(StageSelection prev, int level) throws IOException, InterruptedException {
		this.prev = prev;
		frame = prev.frame;
		this.level = level;
		game_speed = 1;
		init();
	}
	
	public void init() throws IOException, InterruptedException {
		frame.setContentPane(this);

		mineral = 30;
		life = 20;
		
		map = new Game(level);
		
		btn = new JButton[4];
		towerInfo = new TowerInfo[TOWER_NUM];
		towerBuyBtn = new JButton[TOWER_NUM];
		
		for(int i=0; i<2; i++) {
			for(int j=0; j<5; j++) {
				towerBuyBtn[i*5+j] = new JButton("Mon "+(i*5+j+1));
				towerBuyBtn[i*5+j].setSize((Device.dim.width - Device.dim.height)/5, (Device.dim.width - Device.dim.height)/5);
				towerBuyBtn[i*5+j].setLocation(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4 + (Device.dim.width - Device.dim.height)/5*j,
						Device.dim.height/70 + Device.dim.height/100 + (Device.dim.width - Device.dim.height)/5*i);
				towerBuyBtn[i*5+j].setFont(new Font("궁서",Font.BOLD,Device.dim.height/60));
				towerBuyBtn[i*5+j].setHorizontalAlignment(JLabel.CENTER);
				frame.add(towerBuyBtn[i*5+j]);
				towerBuyBtn[i*5+j].addActionListener(this);
			}
		}
		
		for(int i=0; i<TOWER_NUM; i++) {
			towerInfo[i] = Main.c.towerInfo(i+1).info;
		}

		btn[0] = new JButton("×1");
		btn[1] = new JButton("SKIP");
		btn[2] = new JButton("UNMUTE");
		btn[3] = new JButton("MENU");
		
		for(int i=0; i<4; i++) {
			/* 버튼에 이미지 설정
			ImageIcon icon = new ImageIcon("/Users/baek/Downloads/marvel.jpg");
			Image image = icon.getImage();
			image = image.getScaledInstance((Device.dim.width - Device.dim.height)/5, (Device.dim.width - Device.dim.height)/5, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			btn[i] = new JButton(icon);
			*/
			btn[i].setSize((Device.dim.width - Device.dim.height)/5, (Device.dim.width - Device.dim.height)/5);
			btn[i].setLocation(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4 + (Device.dim.width - Device.dim.height)/5*i/3*4,
					Device.dim.height/100*82);
			btn[i].setFont(new Font("궁서",Font.BOLD,Device.dim.height/60));
			btn[i].setHorizontalAlignment(JLabel.CENTER);
			frame.add(btn[i]);
			btn[i].addActionListener(this);
		}
		
		setTimer();
	    
	    this.addMouseListener(this);
	    
		frame.setVisible(true);
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		
		drawTimer = new DrawTimer(this);
	    jobScheduler.scheduleAtFixedRate(drawTimer, 0, 1000/30);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		map.drawMap(g);
		
		//info window
		g.drawRect(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2,
				(Device.dim.width - Device.dim.height),
				Device.dim.height/100*79 - (Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn[0]) {
			if(game_speed == 1) {
				btn[0].setText("×2");
				game_speed = 2;
			} else if(game_speed == 2) {
				btn[0].setText("×4");
				game_speed = 4;
			} else {
				btn[0].setText("×1");
				game_speed = 1;
			}
		} else if(e.getSource() == btn[1]) {
			
		} else if(e.getSource() == btn[2]) {
			if(btn[2].getText().equals("UNMUTE"))
				btn[2].setText("MUTE");
			else
				btn[2].setText("UNMUTE");
		} else if(e.getSource() == btn[3]) {
			jobScheduler.cancel();
			drawTimer.cancel();
			map.cancelTimer();
			prev.resume();
		} else {
			for(int i=0; i<TOWER_NUM; i++) {
				if(e.getSource() == towerBuyBtn[i]) {
					if(selected == i-100)
						selected = 0;
					else
						selected = i-100;
				}
			}
		}
	}
	
	public void clickEvent(MouseEvent e) {
		if((e.getX() >= Device.dim.height/35 + Device.dim.height/70/50 &&
				e.getX() <= Device.dim.height/35*33 + Device.dim.height/70/50) &&
				(e.getY() >= Device.dim.height/70 + Device.dim.height/100 &&
				e.getY() <= Device.dim.height/70*65 + Device.dim.height/100)) {
			int x,y;
			x = (e.getX() - (Device.dim.height/35 + Device.dim.height/70/50)) / (Device.dim.height/35);
			y = (e.getY() - (Device.dim.height/70 + Device.dim.height/100)) / (Device.dim.height/35);
			
			if(selected >= -100 && selected <= -91)
				if(Main.user.level >= towerInfo[selected+100].unlock_level) {
					map.buildTower(x, y, selected+100);
				}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		clickEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
