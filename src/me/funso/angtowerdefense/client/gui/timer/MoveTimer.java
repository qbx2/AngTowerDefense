package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.Map;
import me.funso.angtowerdefense.Monster;
import me.funso.angtowerdefense.client.gui.GameMain;

public class MoveTimer extends TimerTask {

	private int j;
	Monster monster;
	int index;
	
	public MoveTimer(Monster monster, int index) {
		j=1;
		this.monster = monster;
		this.index = index;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			if(!monster.move()) {
				Map.monster[index] = null;
				this.cancel();
			}
		}
		j++;
	}

}
