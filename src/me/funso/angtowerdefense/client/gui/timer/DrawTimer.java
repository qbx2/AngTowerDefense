package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.client.gui.game.GameMain;

public class DrawTimer extends TimerTask {

	GameMain gm;
	
	public DrawTimer(GameMain gm) {
		this.gm = gm;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		gm.repaint();
	}

}
