package me.funso.angtowerdefense.client.gui.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;

import javax.swing.*;

import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.StageSelection;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;
import me.funso.angtowerdefense.client.gui.game.tower.TowerManager;
import me.funso.angtowerdefense.client.gui.timer.DrawTimer;
import me.funso.angtowerdefense.client.gui.timer.WaveTimer;

public class GameMain extends Container implements ActionListener, MouseListener, Paintable {
	
	final int TOWER_NUM = 10;
	private StageSelection prev;
	private JButton btn[];
	private JButton[] towerBuyBtn;

	private int mineral;
	public static int life;
	public static int level;
	
	private int selected;
	public static int game_speed;
	public static int[] monsterType;
	
	DrawTimer drawTimer;
    Timer jobScheduler;

	ImageIcon icon[][];

	public static GameMain gm;
	private Game game;

	private JLabel lcg;
	
	public GameMain(StageSelection prev, int level) throws IOException, InterruptedException {
		this.prev = prev;
		this.level = level;
		game_speed = 1;
		init();
		gm = this;
	}
	
	public void init() throws IOException, InterruptedException {
		Main.frame.setContentPane(this);

		monsterType = new int[Main.stageInfo[level-1].gen_rule.length()];
		for(int i=0; i<Main.stageInfo[level-1].gen_rule.length(); i++) {
			monsterType[i] = Main.stageInfo[level-1].gen_rule.charAt(i) - '0';
		}

		mineral = 50;
		life = 20;

		game = new Game();

		btn = new JButton[4];
		towerBuyBtn = new JButton[TOWER_NUM];
		
		for(int i=0; i<2; i++) {
			for(int j=0; j<5; j++) {
				towerBuyBtn[i*5+j] = new JButton("Tower "+(i*5+j+1));
				towerBuyBtn[i*5+j].setSize((Device.dim.width - Device.dim.height)/5, (Device.dim.width - Device.dim.height)/5);
				towerBuyBtn[i*5+j].setLocation(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4 + (Device.dim.width - Device.dim.height)/5*j,
						Device.dim.height/70 + Device.dim.height/100 + (Device.dim.width - Device.dim.height)/5*i);
				towerBuyBtn[i*5+j].setFont(new Font("궁서",Font.BOLD,Device.dim.height/60));
				towerBuyBtn[i*5+j].setHorizontalAlignment(JLabel.CENTER);
				Main.frame.add(towerBuyBtn[i*5+j]);
				towerBuyBtn[i*5+j].addActionListener(this);
			}
		}

		icon = new ImageIcon[4][];
		icon[0] = new ImageIcon[3];
		icon[1] = new ImageIcon[1];
		icon[2] = new ImageIcon[2];
		icon[3] = new ImageIcon[1];

		icon[0][0] = new ImageIcon("img/btn/x1.png");
		icon[0][1] = new ImageIcon("img/btn/x2.png");
		icon[0][2] = new ImageIcon("img/btn/x4.png");
		icon[1][0] = new ImageIcon("img/btn/skip.png");
		icon[2][0] = new ImageIcon("img/btn/unmute.png");
		icon[2][1] = new ImageIcon("img/btn/mute.png");
		icon[3][0] = new ImageIcon("img/btn/menu.png");

		for(int i=0; i<icon.length; i++) {
			for(int j=0; j<icon[i].length; j++) {
				Image img = icon[i][j].getImage();
				img = img.getScaledInstance((Device.dim.width - Device.dim.height)/6, (Device.dim.width - Device.dim.height)/6, java.awt.Image.SCALE_SMOOTH);
				icon[i][j] = new ImageIcon(img);
			}
		}
		
		for(int i=0; i<4; i++) {
			btn[i] = new JButton(icon[i][0]);

			btn[i].setSize((Device.dim.width - Device.dim.height)/5, (Device.dim.width - Device.dim.height)/5);
			btn[i].setLocation(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4 + (Device.dim.width - Device.dim.height)/5*i/3*4,
					Device.dim.height/100*82);
			btn[i].setHorizontalAlignment(JLabel.CENTER);
			Main.frame.add(btn[i]);
			btn[i].addActionListener(this);
		}

		setTimer();

		ImageIcon icon = new ImageIcon("img/LCG.png");
		Image img = icon.getImage();
		img = img.getScaledInstance(Device.dim.width*245/1600, Device.dim.width*300/1600, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		lcg = new JLabel(icon);
		lcg.setSize(Device.dim.width*245/1600, Device.dim.width*300/1600);
		lcg.setLocation(Device.dim.width/100*85, Device.dim.height/100*45);
		lcg.setHorizontalAlignment(JLabel.CENTER);
		Main.frame.add(lcg);

	    this.addMouseListener(this);

		Main.frame.setVisible(true);
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		drawTimer = new DrawTimer(this);
	    jobScheduler.scheduleAtFixedRate(drawTimer, 0, 1000/60);
	}

	public void cancelTimer() {
		game.cancelTimer();
		jobScheduler.cancel();
		drawTimer.cancel();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		game.paint(g);
		
		//info window
		g.drawRect(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2,
				(Device.dim.width - Device.dim.height),
				Device.dim.height/100*79 - (Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2));

		g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/50));
		g.setColor(Color.black);
		g.drawString("User Level : "+Main.user.level,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/100*78 - Device.dim.height/40*3);
		g.drawString("Wave : "+Game.waveTimer.wave+"/10",
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/100*78 - Device.dim.height/40*2);
		g.drawString("Mineral : "+mineral,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/100*78 - Device.dim.height/40);
		g.drawString("Life : "+life+"/20",
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/100*78);

		if(life <= 0) {
			g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
			g.drawString("GAME",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*4);
			g.drawString("OVER",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*9);
			return;
		}

		if(WaveTimer.wave >= 10 && MonsterManager.monsters.size() == 0) {
			g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
			g.drawString("You",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*4);
			g.drawString("Win",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*9);
			cancelTimer();
			return;
		}

		if(-100 <= selected && selected <= 9-100) {
			paintTowerInfo(g, selected + 100);
			if(Main.user.level < Main.towerInfo[selected+100].unlock_level) {
				g.setColor(Color.RED);
			}
			g.drawString("Level Limit : " + Main.towerInfo[selected+100].unlock_level,
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*7);
			if(mineral < Main.towerInfo[selected+100].cost) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString("Cost : " + Main.towerInfo[selected+100].cost,
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*8);
		} else if(selected == -99999) {
			g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
			g.drawString("Mineral Shortage",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/20);
		} else if(selected == -99998) {
			g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
			g.drawString("Level Limit",
					Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
					Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/20);
		} else if(selected != 0) {
			if(game.tile[(selected/100)-1][(selected%100)-1].getTower() != null) {
				paintTowerInfo(g, game.tile[(selected/100)-1][(selected%100)-1].getTowerType());
				g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/50));
				g.setColor(Color.BLACK);
				g.drawString("Kill : " + game.tile[(selected/100)-1][(selected%100)-1].getTower().getKill(),
						Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
						Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*7);
			}
		}
	}

	public void paintTowerInfo(Graphics g, int type) {
		g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/20));
		g.drawString(Main.towerInfo[type].name,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*2);
		g.setFont(new Font("궁서",Font.BOLD,Device.dim.height/50));
		g.drawString(Main.towerInfo[type].description,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*3);
		g.drawString("Damage : " + Main.towerInfo[type].damage,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*4);
		g.drawString("Speed : " + Main.towerInfo[type].attack_speed,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*5);
		g.drawString("Range : " + Main.towerInfo[type].attack_range,
				Device.dim.height + Device.dim.height/70 - Device.dim.height/100*3,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2 + Device.dim.height/40*6);
	}

	public void earnMineral(int mineral) { this.mineral += mineral; }

	public void payMineral(int towerType) {
		mineral -= Main.towerInfo[towerType].cost;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn[0]) {
			Game.waveTimer.check();
			if(game_speed == 1) {
				btn[0].setIcon(icon[0][1]);
				game_speed = 2;
			} else if(game_speed == 2) {
				btn[0].setIcon(icon[0][2]);
				game_speed = 4;
			} else {
				btn[0].setIcon(icon[0][0]);
				game_speed = 1;
			}
		} else if(e.getSource() == btn[1]) {
			game.waveTimer.waveStart();
		} else if(e.getSource() == btn[2]) {
			if(btn[2].getIcon().equals(icon[2][0]))
				btn[2].setIcon(icon[2][1]);
			else
				btn[2].setIcon(icon[2][0]);
		} else if(e.getSource() == btn[3]) {
			cancelTimer();
			MonsterManager.monsters.clear();
			TowerManager.towers.clear();
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
				if(Main.user.level >= Main.towerInfo[selected+100].unlock_level) {
					if(mineral >= Main.towerInfo[selected+100].cost) {
						if(game.buildTower(x, y, selected+100))
							payMineral(selected+100);
					} else {
						//mineral shortage message
						selected = -99999;
					}
					if(selected == -91)
						selected = 0;
				} else {
					//level limit message
					selected = -99998;
				}
			else {
				selected = (x+1)*100 + y+1;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
