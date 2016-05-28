package me.funso.angtowerdefense.client.gui.game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import me.funso.angtowerdefense.MapParser;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.StageSelection;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;
import me.funso.angtowerdefense.client.gui.game.tower.Tower;
import me.funso.angtowerdefense.client.gui.game.tower.TowerManager;
import me.funso.angtowerdefense.client.gui.timer.MonsterRegenTimer;
import me.funso.angtowerdefense.client.gui.timer.WaveTimer;

public class Game implements Paintable, TimerSettable {

	final int SIZE = 32;
	private Tile[][] tile;

	public static MonsterManager monsterManager;
	public static TowerManager towerManager;

    Timer jobScheduler;
	WaveTimer waveTimer;
	
	public Game() throws IOException, InterruptedException {
		init();
		setTimer();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);
		waveTimer = new WaveTimer(GameMain.monsterType);
		jobScheduler.schedule(waveTimer, 10000, 30000);
	}
	
	public void cancelTimer() {
		jobScheduler.cancel();
		waveTimer.cancelTimer();
		waveTimer.cancel();
		for(int i=0; i<towerManager.towers.size(); i++) {
			towerManager.towers.get(i).cancelTimer();
		}
		for(int i=0; i<monsterManager.monsters.size(); i++) {
			if(monsterManager.monsters.get(i) != null) {
				monsterManager.monsters.get(i).cancelTimer();
			}
		}
	}
	
	public boolean buildTower(int x, int y, int type) {
		Tower t = tile[x][y].buildTower(type);
		if(t != null) {
			towerManager.towers.add(t);
			return true;
		}
		return false;
	}
	
	public void init() throws IOException, InterruptedException {
		monsterManager = new MonsterManager();
		towerManager = new TowerManager();

		tile = getMap();
	}
	
	public void paint(Graphics g) {
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j].paint(g);
		
		for(int i=0; i<monsterManager.monsters.size(); i++)
			if(monsterManager.monsters.get(i) != null)
				monsterManager.monsters.get(i).paint(g);
	}
	
	public Tile[][] getMap() throws IOException, InterruptedException {
		return MapParser.parse(Main.c.loadMap(Main.stageInfo[GameMain.level-1].map_idx).map);
	}
}
