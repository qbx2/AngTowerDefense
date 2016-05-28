package me.funso.angtowerdefense.client.gui.game.monster;

import java.awt.Graphics;
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
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

public abstract class Monster implements Paintable, TimerSettable {
	
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

	public Monster() throws IOException, InterruptedException {
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
		if(hp <= 0)
			return false;
		return true;
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
		if(size_x == 0) {
			size_x = g.getFontMetrics().stringWidth("M");
			size_y = g.getFontMetrics().getHeight()/3*2;
			randMoveCalc();
		}
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
