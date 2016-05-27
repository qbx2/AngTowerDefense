package me.funso.angtowerdefense.client.gui.timer;

import java.io.IOException;
import java.util.TimerTask;

import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.GameMain;

public class MonsterRegenTimer extends TimerTask {

	private int i, j;
	private int type;
	
	public MonsterRegenTimer(int type) {
		i=0;
		j=1;
		this.type = type;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			if(i < 30) {
				try {
					Game.monsterManager.regen(type);
						//modify later
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
			}
		}
		j++;
	}

}
