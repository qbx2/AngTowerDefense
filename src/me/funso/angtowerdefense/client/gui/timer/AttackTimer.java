package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.Tower;
import me.funso.angtowerdefense.client.gui.GameMain;

public class AttackTimer extends TimerTask {

	Tower tower;
	private int j;
	
	public AttackTimer(Tower tower) {
		j=1;
		this.tower = tower;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			tower.attack();
		}
		j++;
	}

}
