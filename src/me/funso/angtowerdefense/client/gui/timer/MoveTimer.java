package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

public class MoveTimer extends TimerTask {

	private int j;
	Monster monster;
	
	public MoveTimer(Monster monster) {
		j=1;
		this.monster = monster;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			if(!monster.move()) {
				MonsterManager.monsters.remove(monster);
				GameMain.life--;
				if(GameMain.life <= 0) {
					GameMain.gm.repaint();
					GameMain.gm.cancelTimer();
				}
				this.cancel();
			}
		}
		j++;
	}

}
