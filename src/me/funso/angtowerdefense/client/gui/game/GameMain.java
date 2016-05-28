package me.funso.angtowerdefense.client.gui.game;

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
import javax.swing.JLabel;

import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.StageSelection;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;
import me.funso.angtowerdefense.client.gui.game.tower.TowerManager;
import me.funso.angtowerdefense.client.gui.timer.DrawTimer;

public class GameMain extends Container implements ActionListener, MouseListener, Paintable {
	
	final int TOWER_NUM = 10;

	private StageSelection prev;
	private JButton btn[];
	
	private JButton[] towerBuyBtn;
	
	private Game game;
	
	private int mineral;
	private int life;
	public static int level;
	
	private int selected;
	public static int game_speed;
	public static int[] monsterType;
	
	DrawTimer drawTimer;
    Timer jobScheduler;
	
	public GameMain(StageSelection prev, int level) throws IOException, InterruptedException {
		this.prev = prev;
		this.level = level;
		game_speed = 1;
		init();
	}
	
	public void init() throws IOException, InterruptedException {
		Main.frame.setContentPane(this);

		System.out.println(Main.stageInfo[level-1].gen_rule);
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
			Main.frame.add(btn[i]);
			btn[i].addActionListener(this);
		}

		setTimer();

	    this.addMouseListener(this);

		Main.frame.setVisible(true);
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		drawTimer = new DrawTimer(this);
	    jobScheduler.scheduleAtFixedRate(drawTimer, 0, 1000/30);
	}

	public void cancelTimer() {
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
	}

	public void payMineral(int towerType) {
		mineral -= Main.towerInfo[towerType].cost;
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
			cancelTimer();
			game.cancelTimer();
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
						//mineral lack messege
					}
				} else {
					//level limit messege
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
