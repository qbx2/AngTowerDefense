package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.client.gui.game.tower.Tower;
import me.funso.angtowerdefense.client.gui.game.GameMain;

public class AttackTimer extends TimerTask {

	Tower tower;
	private int i;
	
	public AttackTimer(Tower tower) {
		i=1;
		this.tower = tower;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*i >= 4) {
			i=0;
			tower.attack();
		}
		i++;
	}

}
