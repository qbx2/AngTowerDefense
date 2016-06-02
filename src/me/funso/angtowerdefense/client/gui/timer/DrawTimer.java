package me.funso.angtowerdefense.client.gui.timer;

import java.util.TimerTask;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.gui.game.GameMain;

public class DrawTimer extends TimerTask {

	GameMain gm;
	
	public DrawTimer(GameMain gm) {
		this.gm = gm;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		gm.repaint(Device.dim.height + Device.dim.height/70 - Device.dim.height/100*4,
				Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2,
				(Device.dim.width - Device.dim.height),
				Device.dim.height/100*79 - (Device.dim.height/70 + Device.dim.height/20 + (Device.dim.width - Device.dim.height)/5*2));
		gm.repaint(Device.dim.height/35 + Device.dim.height/70/50 - Device.dim.height/70,
				Device.dim.height/100 + Device.dim.height/35/2,
				Device.dim.height/35*32, Device.dim.height/35*32 + Device.dim.height/35/2);
	}

}
