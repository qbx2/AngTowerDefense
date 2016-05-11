package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.Monster;
import me.funso.angtowerdefense.Tile;
import me.funso.angtowerdefense.Tower;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.astar.Map;
import me.funso.angtowerdefense.client.astar.Point;
import me.funso.angtowerdefense.client.gui.timer.AttackTimer;
import me.funso.angtowerdefense.client.gui.timer.DrawTimer;
import me.funso.angtowerdefense.client.gui.timer.MonsterRegenTimer;
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

public class GameMain extends Container implements ActionListener, MouseListener {
	
	final int SIZE = 32;
	final int TOWER_NUM = 10;
	
	JFrame frame;
	private StageSelection prev;
	private JButton btn[];
	
	private Tile[][] tile;
	public static Monster[] monster;
	
	private JButton[] towerBuyBtn;
	private Tower[] tower_info;
	private ArrayList<Tower> tower;
	
	private String route;
	private char[][] tileType;
	
	private Graphics g;
	
	private int mineral;
	private int life;
	private int level;
	
	private int selected;
	public static int game_speed;
	
	DrawTimer drawTimer;
	MonsterRegenTimer regenTimer;
    Timer jobScheduler;
    
    Point start;
	
	public GameMain(StageSelection prev, int level) throws IOException {
		this.prev = prev;
		frame = prev.frame;
		this.level = level;
		game_speed = 1;
		init();
	}
	
	public void init() throws IOException {
		frame.setContentPane(this);

		mineral = 30;
		life = 20;
		
		tile = new Tile[SIZE][SIZE];
		tileType = new char[SIZE][SIZE];
		btn = new JButton[4];
		monster = new Monster[30];
		tower = new ArrayList<Tower>();
		tower_info = new Tower[TOWER_NUM];
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
			tower_info[i] = new Tower(-999,-999,-999,-999,i);
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

		route = astar();

		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j] = new Tile(j,i,tileType[i][j]);
		
		setTimer();
	    
	    this.addMouseListener(this);
	    
		frame.setVisible(true);
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		
		drawTimer = new DrawTimer(this);
	    jobScheduler.scheduleAtFixedRate(drawTimer, 0, 1000/30);

	    regenTimer = new MonsterRegenTimer(null, start, route);
	    jobScheduler.schedule(regenTimer, 100, 300/4);
	}
	
	public String astar() throws IOException {
		
		//use variable 'level', get the map from db, thank you!
		
		File file = new File("/Users/baek/Documents/workspace/AngTowerDefense/stage2.txt");
		FileInputStream input = new FileInputStream(file);
		byte buf[] = new byte[input.available()];
		input.read(buf);
		input.close();
		Map map = new Map(new String(buf));
		tileType = map.getMap();
		start = map.find('S');
		
		return map.aStar(map.find('S'), map.find('G'));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		//tile
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j].drawTile(g);
		
		for(int i=0; i<monster.length; i++)
			if(monster[i] != null)
				monster[i].drawMonster(g);
		
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
			regenTimer.cancel();
			for(int i=0; i<tower.size(); i++) {
				tower.get(i).cancelTimer();
			}
			for(int i=0; i<monster.length; i++) {
				if(monster[i] != null) {
					monster[i].cancelTimer();
				}
			}
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
				if(Main.user.level >= tower_info[selected+100].levelLimit) {
					Tower t = tile[y][x].buildTower(selected+100);
					tower.add(t);
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
