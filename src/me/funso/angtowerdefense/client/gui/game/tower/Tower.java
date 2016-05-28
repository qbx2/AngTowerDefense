package me.funso.angtowerdefense.client.gui.game.tower;


import java.awt.Graphics;
import java.sql.Time;
import java.util.Timer;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.Paintable;
import me.funso.angtowerdefense.client.gui.game.TimerSettable;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;
import me.funso.angtowerdefense.client.gui.timer.AttackTimer;

public abstract class Tower implements Paintable, TimerSettable {
	
	int r_x,r_y;		//tile's center xy
	int size;			//xy size;
	int x,y;			//index in map;

	String name;
	int damage, attack_speed, attack_range;
	Monster target;
	public boolean attack;
	int type;
	int kill;

	AttackTimer attackTimer;
    Timer jobScheduler;
	
	public Tower(int x, int y, int r_x, int r_y, int type) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.r_x = r_x;
		this.r_y = r_y;
		size = Device.dim.height/40;
		attack = false;
		kill = 0;
	}
	
	public void cancelTimer() {
		jobScheduler.cancel();
		attackTimer.cancel();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
	    attackTimer = new AttackTimer(this);
	    jobScheduler.scheduleAtFixedRate(attackTimer, 100, attack_speed*10);
	}
	
	public void attack() {

		int monster_x, monster_y;

		if(target != null) {
			monster_x = target.getX();
			monster_y = target.getY();
			if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
				attack = true;
				if(target.damaged(damage)) {		//didn't die

				} else {		//die
					for(int i = 0; i< Game.monsterManager.monsters.size(); i++) {
						if(MonsterManager.monsters.get(i) == target) {
							MonsterManager.monsters.remove(i);
							target = null;
							kill++;
							return;
						}
					}
				}
				return;
			}
		}
		
		for(int i=0; i<MonsterManager.monsters.size(); i++) {
			if(MonsterManager.monsters.get(i) != null) {
				monster_x = MonsterManager.monsters.get(i).getX();
				monster_y = MonsterManager.monsters.get(i).getY();
				if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
					target = MonsterManager.monsters.get(i);
					attack = true;
					if(target.damaged(damage)) {
						
					} else {	//die
						MonsterManager.monsters.remove(i);
						kill++;
						target = null;
					}
					return;
				}
			}
		}
		
		target = null;
	}
	
	public void paint(Graphics g) {
		g.drawArc(r_x-size/2, r_y-size/2, size, size, 0, 360);
	}

	public int getType() { return type; }

	public int getKill() { return kill; }

}
