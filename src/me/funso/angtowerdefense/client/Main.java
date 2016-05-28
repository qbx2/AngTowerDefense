package me.funso.angtowerdefense.client;

import java.io.IOException;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.StageInfo;
import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.User;
import me.funso.angtowerdefense.client.gui.MainMenu;

import javax.swing.*;

public class Main {
	
	public static Client c;
	public static User user;
	public static JFrame frame = new JFrame();

	public static TowerInfo[] towerInfo;
	public static MonsterInfo[] monsterInfo;
	public static StageInfo[] stageInfo;

	public static void main(String args[]) throws InterruptedException {
		
		try {
			c = new Client("v.funso.me", 15349);

			towerInfo = new TowerInfo[10];

			for(int i=0; i<towerInfo.length; i++) {
				towerInfo[i] = c.towerInfo(i+1).info;
			}

			monsterInfo = new MonsterInfo[6];

			for(int i=0; i<monsterInfo.length; i++) {
				monsterInfo[i] = c.monsterInfo(i+1).info;
			}

			stageInfo = new StageInfo[Main.c.stageCount().count];

			for(int i=0; i<stageInfo.length; i++) {
				stageInfo[i] = c.stageInfo(i+1).info;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Device.dim.width, Device.dim.height);
		new MainMenu();
	}
}
