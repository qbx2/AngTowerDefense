package me.funso.angtowerdefense;


import java.awt.Graphics;
import java.util.Timer;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.gui.timer.AttackTimer;

public class Tower {
	
	private int r_x,r_y;		//tile's center xy
	private int size;			//xy size;
	private int x,y;			//index in map;
	
	//db data
	private int type;		//idx
	private String name;
	private int damage, attack_speed, attack_range;
	private Monster target;
	public int cost, levelLimit;		//cost, unlock_level

	AttackTimer attackTimer;
    Timer jobScheduler;
	
	public Tower(int x, int y, int r_x, int r_y, int type) {
		this.x = x;
		this.y = y;
		this.r_x = r_x;
		this.r_y = r_y;
		size = Device.dim.height/40;
		
		this.type = type;
		//use type(idx), load below values from db
		cost = 10;
		levelLimit = 1;
		attack_range = size*3;
		attack_speed = 3;
		damage = 5;
		
		setTimer();
	}
	
	public void cancelTimer() {
		jobScheduler.cancel();
		attackTimer.cancel();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);

	    attackTimer = new AttackTimer(this);
	    jobScheduler.scheduleAtFixedRate(attackTimer, 100, 300/attack_speed/4);
	}
	
	public void attack() {
		int monster_x, monster_y;
		
		if(target != null) {
			if(target != null) {
				monster_x = target.getX();
				monster_y = target.getY();
				if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
					if(target.damaged(damage)) {		//didn't die
						
					} else {		//die
						for(int i=0; i<Map.monster.length; i++) {
							if(Map.monster[i] == target) {
								Map.monster[i] = null;
								return;
							}
						}
					}
					return;
				}
			}
		}
		
		for(int i=0; i<Map.monster.length; i++) {
			if(Map.monster[i] != null) {
				monster_x = Map.monster[i].getX();
				monster_y = Map.monster[i].getY();
				if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
					target = Map.monster[i];
					if(target.damaged(damage)) {
						
					} else {		//die
						Map.monster[i] = null;
					}
					return;
				}
			}
		}
		
		target = null;
	}
	
	public void drawTower(Graphics g) {
		g.drawArc(r_x-size/2, r_y-size/2, size, size, 0, 360);
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
