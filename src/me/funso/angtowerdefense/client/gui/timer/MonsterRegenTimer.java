package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.Monster;
import me.funso.angtowerdefense.client.astar.Point;
import me.funso.angtowerdefense.client.gui.GameMain;

public class MonsterRegenTimer extends TimerTask {

	private int i, j;
	Point start;
	String route;
	
	public MonsterRegenTimer(int[] type, Point start, String route) {
		i=0;
		j=1;
		this.start = start;
		this.route = route;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			if(i < GameMain.monster.length) {
				GameMain.monster[i] = new Monster(0,start,route,i);
				i++;
			}
		}
		j++;
	}

}
