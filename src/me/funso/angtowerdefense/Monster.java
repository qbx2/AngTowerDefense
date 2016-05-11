package me.funso.angtowerdefense;

import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.astar.Point;
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

public class Monster {
	
	private int x,y;
	private String route, newRoute;
	private int i;
	private int[] move;
	private int size_x,size_y;
	private int start_x,start_y;

	private final int type;			//idx
	private String name;			//name
	private int armor, speed, hp;	//
	
	Timer jobScheduler;
	MoveTimer moveTimer;
	
	public Monster(int type, Point start, String route, int index) {
		this.type = type;
		this.route = route;
		move = new int[route.length()];
		i=0;
		start_x = start.x;
		start_y = start.y;
		size_x = size_y = 0;
		
		
		//for test, it will load from db
		hp = 5;
		armor = 0;
		speed = 20;
		
		
		setTimer(index);
	}
	
	public void cancelTimer() {
		jobScheduler.cancel();
		moveTimer.cancel();
	}
	
	public void setTimer(int index) {
		jobScheduler = new Timer(true);
		moveTimer = new MoveTimer(this, index);
		jobScheduler.scheduleAtFixedRate(moveTimer, 100, speed/4);
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
	
	public void drawMonster(Graphics g) {
		if(size_x == 0) {
			size_x = g.getFontMetrics().stringWidth("M");
			size_y = g.getFontMetrics().getHeight()/3*2;
			randMoveCalc();
		}
		g.drawString("M", x-size_x/2, y+size_y/2);
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
}
