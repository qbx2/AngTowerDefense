package me.funso.angtowerdefense.client.gui.timer;

import java.util.Timer;
import java.util.TimerTask;

public class WaveTimer extends TimerTask {

	int i;
	int[] type;
	Timer jobScheduler;
	MonsterRegenTimer regenTimer;

	public WaveTimer(int[] type) {
		i=0;
		this.type = type;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(i<type.length) {
			jobScheduler = new Timer(true);
			regenTimer = new MonsterRegenTimer(type[i]);
			jobScheduler.schedule(regenTimer, 100, 300 / 4);
			i++;
		}
	}

	public void cancelTimer() {
		jobScheduler.cancel();
		regenTimer.cancel();
	}
}
