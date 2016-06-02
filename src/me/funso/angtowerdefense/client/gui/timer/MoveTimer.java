package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

public class MoveTimer extends TimerTask {

	private int i,j,k;
	Monster monster;
	
	public MoveTimer(Monster monster) {
		j=1;
		i=0;
		k=0;
		this.monster = monster;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			i++;
			if(monster.isFreeze && k<=50) {
				k++;
				return;
			}
			k=0;
			monster.isFreeze = false;
			if(monster.isSwamp && i%2 == 1)
				return;
			if(!monster.move() && !monster.isDead()) {
				MonsterManager.monsters.remove(monster);
				GameMain.life--;
				monster = null;
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
