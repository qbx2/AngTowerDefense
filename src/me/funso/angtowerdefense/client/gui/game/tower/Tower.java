package me.funso.angtowerdefense.client.gui.game.tower;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

import javax.imageio.ImageIO;

public abstract class Tower implements Paintable, TimerSettable {

	public static Image[] image;
	
	int r_x,r_y;		//tile's center xy
	int size_x, size_y;			//xy size;
	int x,y;			//index in map;

	String name;
	int damage, attack_speed, attack_range;
	Monster target;
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
		kill = 0;

		image = new Image[9];
		try {
			image[0] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/fast.png"));
			image[1] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/thunder.png"));
			image[2] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/lazer.png"));
			image[3] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/swamp.png"));
			image[4] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/mineral.png"));
			image[5] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/bomb.png"));
			image[6] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/poison.png"));
			image[7] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/freeze.png"));
			image[8] = ImageIO.read(getClass().getClassLoader().getResource("img/tower/surprisebox.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				target.attackWait();
				if(target.damaged(damage)) {		//didn't die
					target.attackRelease();
				} else {		//die
					for(int i = 0; i< Game.monsterManager.monsters.size(); i++) {
						if(MonsterManager.monsters.get(i) == target) {
							if(MonsterManager.type.size() > i) {
								GameMain.gm.earnMineral(MonsterManager.type.get(i));
								MonsterManager.type.remove(i);
							}
							if(MonsterManager.monsters.size() > i) {
								MonsterManager.monsters.remove(i);
							}
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
					target.attackWait();
					if(target.damaged(damage)) {
						target.attackRelease();
					} else {	//die
						if(MonsterManager.type.size() > i) {
							GameMain.gm.earnMineral(MonsterManager.type.get(i));
							MonsterManager.type.remove(i);
						}
						if(MonsterManager.monsters.size() > i) {
							MonsterManager.monsters.remove(i);
						}
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
		g.drawArc(r_x-size_x/2, r_y-size_y/2, size_x, size_y, 0, 360);
	}

	public int getType() { return type; }

	public int getKill() { return kill; }

}
