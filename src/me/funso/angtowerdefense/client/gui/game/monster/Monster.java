package me.funso.angtowerdefense.client.gui.game.monster;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.astar.Map;
import me.funso.angtowerdefense.client.astar.Point;
import me.funso.angtowerdefense.client.gui.game.Paintable;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.TimerSettable;
import me.funso.angtowerdefense.client.gui.game.tower.PoisonTower;
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

import javax.imageio.ImageIO;

public abstract class Monster implements Paintable, TimerSettable {

	public Image[] image;

	int x,y;
	String route, newRoute;
	int i;
	int[] move;
	int size_x,size_y;
	int start_x,start_y;

	String name;
	int armor, speed, hp;
	
	Point start;
	
	Timer jobScheduler;
	MoveTimer moveTimer;

	public boolean isFreeze = false;
	public boolean isSwamp = false;
	public boolean isPoison = false;
	public PoisonTower pt = null;

	public boolean isAttack = false;

	public Monster() throws IOException, InterruptedException {
		image = new Image[6];
		image[0] = ImageIO.read(new File("img/monster/zergling.png"));
		image[1] = ImageIO.read(new File("img/monster/golem.png"));
		image[2] = ImageIO.read(new File("img/monster/citizen.png"));
		image[3] = ImageIO.read(new File("img/monster/shaco.png"));
		image[4] = ImageIO.read(new File("img/monster/bee.png"));
		image[5] = ImageIO.read(new File("img/monster/boss.png"));
		route = astar();
		move = new int[route.length()];
		i=0;
		start_x = start.x;
		start_y = start.y;
		size_x = size_y = 0;
	}
	
	public void cancelTimer() {
		jobScheduler.cancel();
		moveTimer.cancel();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		moveTimer = new MoveTimer(this);
		jobScheduler.scheduleAtFixedRate(moveTimer, 100, 100/speed);
	}
	
	public void randMoveCalc() {
		Random rand = new Random();
		
		newRoute = new String("");
		char tmp;
		int index=0, tmp_xy=0, i=0;
		tmp = route.charAt(i);
		newRoute = newRoute.concat(""+tmp);
		
		switch(tmp) {
		case 'u':
			x = Device.dim.height/35*(start_x+1)+Device.dim.height/70/50 + size_x/2 + rand.nextInt(Device.dim.height/35-size_x);
			y = Device.dim.height/35*(start_y+2)+Device.dim.height/100 - Device.dim.height/70;
			start_y--;
			break;
		case 'd':
			x = Device.dim.height/35*(start_x+1)+Device.dim.height/70/50 + size_x/2 + rand.nextInt(Device.dim.height/35-size_x);
			y = Device.dim.height/35*(start_y+1)+Device.dim.height/100 - Device.dim.height/70;
			start_y++;
			break;
		case 'r':
			x = Device.dim.height/35*(start_x+1)+Device.dim.height/70/50;
			y = Device.dim.height/35*(start_y+1)+Device.dim.height/100 - Device.dim.height/70 + size_y/2 + rand.nextInt(Device.dim.height/35 - size_y);
			start_x++;
			break;
		case 'l':
			x = Device.dim.height/35*(start_x+2)+Device.dim.height/70/50;
			y = Device.dim.height/35*(start_y+1)+Device.dim.height/100 - Device.dim.height/70 + size_y/2 + rand.nextInt(Device.dim.height/35 - size_y);
			start_x--;
			break;
		}
		
		for(i=1; i<route.length(); i++) {
			if(tmp == route.charAt(i)) {}
			else {
				switch(tmp) {
				case 'u': case 'd':
					tmp_xy = Device.dim.height/35*(start_y+1)+Device.dim.height/100 - Device.dim.height/70
								+ size_y/2 + rand.nextInt(Device.dim.height/35 - size_y);;
					break;
				case 'r': case 'l':
					tmp_xy = Device.dim.height/35*(start_x+1)+Device.dim.height/70/50
								+ size_x/2 + rand.nextInt(Device.dim.height/35-size_x);
					break;
				}
				tmp = route.charAt(i);
				newRoute = newRoute.concat(""+tmp);
				move[index++] = tmp_xy;
			}
			switch(tmp) {
			case 'u':
				start_y--;
				break;
			case 'd':
				start_y++;
				break;
			case 'r':
				start_x++;
				break;
			case 'l':
				start_x--;
				break;
			}
		}
		switch(tmp) {
		case 'u': case 'd':
			tmp_xy = Device.dim.height/35*(start_y+2)+Device.dim.height/100 - Device.dim.height/70;
			break;
		case 'r': case 'l':
			tmp_xy = Device.dim.height/35*(start_x+2)+Device.dim.height/70/50;
			break;
		}
		move[index] = tmp_xy;
	}
	
	public boolean damaged(int damage) {
		hp -= (damage - armor);
		return !isDead();
	}

	public void attackWait() {
		while(isAttack);
		isAttack = true;
	}

	public void attackRelease() {
		isAttack = false;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public boolean move() {
		if(newRoute == null)
			return true;
		if(newRoute.length() <= i)
			return false;
		switch(newRoute.charAt(i)) {
		case 'u':
			if(y<=move[i]) {
				i++;
				move();
			} else
				y-=3;
			break;
		case 'd':
			if(y>=move[i]) {
				i++;
				move();
			} else
				y+=3;
			break;
		case 'r':
			if(x>=move[i]) {
				i++;
				move();
			} else
				x+=3;
			break;
		case 'l':
			if(x<=move[i]) {
				i++;
				move();
			} else
				x-=3;
			break;
		}
		return true;
	}

	public void paint(Graphics g) {

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveX(int x) {
		this.x += x;
	}
	
	public void moveY(int y) {
		this.y += y;
	}
	
	public String astar() throws IOException, InterruptedException {

		Map map = new Map(Main.c.loadMap(Main.stageInfo[GameMain.level-1].map_idx).map);
		start = map.find('S');
		
		return map.aStar(map.find('S'), map.find('G'));
	}
}
