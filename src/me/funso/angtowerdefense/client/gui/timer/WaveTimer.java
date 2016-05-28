package me.funso.angtowerdefense.client.gui.timer;

import me.funso.angtowerdefense.client.gui.game.GameMain;

import java.util.Timer;
import java.util.TimerTask;

public class WaveTimer extends TimerTask {

	public static int wave;
	int i;
	int[] type;
	Timer jobScheduler;
	MonsterRegenTimer regenTimer;

	public WaveTimer(int[] type) {
		wave=0;
		i=0;
		this.type = type;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		check();
		i++;
	}

	public void check() {
		if(wave == 0) {
			if (GameMain.game_speed * i >= 4) {
				waveStart();
			}
		} else {
			if(GameMain.game_speed * i >= 12) {
				waveStart();
			}
		}
	}

	public void waveStart() {
		if (wave < type.length) {
			jobScheduler = new Timer(true);
			regenTimer = new MonsterRegenTimer(type[wave]);
			jobScheduler.scheduleAtFixedRate(regenTimer, 100, 300 / 4);
			wave++;
			i=0;
		}
	}

	public void cancelTimer() {
		if(jobScheduler != null) {
			jobScheduler.cancel();
		}
		if(regenTimer != null) {
			regenTimer.cancel();
		}
	}
}
