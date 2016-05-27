package me.funso.angtowerdefense.client.gui.game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import me.funso.angtowerdefense.MapParser;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;
import me.funso.angtowerdefense.client.gui.game.tower.Tower;
import me.funso.angtowerdefense.client.gui.game.tower.TowerManager;
import me.funso.angtowerdefense.client.gui.timer.MonsterRegenTimer;

public class Game implements Paintable, TimerSettable {

	final int SIZE = 32;
	private Tile[][] tile;
	private char[][] tileType;

	public static MonsterManager monsterManager;
	public static TowerManager towerManager;

	private int level;

    Timer jobScheduler;
	MonsterRegenTimer regenTimer;
	
	public Game(int level) throws IOException, InterruptedException {
		this.level = level;
		init();
		setTimer();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);

	    regenTimer = new MonsterRegenTimer(1);
	    jobScheduler.schedule(regenTimer, 100, 300/4);
	}
	
	public void cancelTimer() {
		for(int i=0; i<towerManager.towers.size(); i++) {
			towerManager.towers.get(i).cancelTimer();
		}
		for(int i=0; i<monsterManager.monsters.size(); i++) {
			if(monsterManager.monsters.get(i) != null) {
				monsterManager.monsters.get(i).cancelTimer();
			}
		}
	}
	
	public void buildTower(int x, int y, int type) {
		Tower t = tile[x][y].buildTower(type);
		towerManager.towers.add(t);
	}


	
	public void init() throws IOException, InterruptedException {
		monsterManager = new MonsterManager(tileType);
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
		return MapParser.parse(Main.c.loadMap(level).map);
	}
}
