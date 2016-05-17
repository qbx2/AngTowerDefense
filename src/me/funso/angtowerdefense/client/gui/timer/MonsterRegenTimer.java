package me.funso.angtowerdefense.client.gui.timer;

import java.io.IOException;
import java.util.TimerTask;

import me.funso.angtowerdefense.Game;
import me.funso.angtowerdefense.Monster;
import me.funso.angtowerdefense.client.gui.GameMain;

public class MonsterRegenTimer extends TimerTask {

	private int i, j;
	char[][] tileType;
	
	public MonsterRegenTimer(int[] type, char[][] tileType) {
		i=0;
		j=1;
		this.tileType = tileType;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(GameMain.game_speed*j >= 4) {
			j=0;
			if(i < Game.monster.length) {
				try {
					Game.monster[i] = new Monster(0,tileType,i);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
		j++;
	}

}
