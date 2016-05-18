package me.funso.angtowerdefense;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.timer.MonsterRegenTimer;

public class Game {

	final int SIZE = 32;
	
	private char[][] tileType;
	private Tile[][] tile;
	
	public static Monster[] monster;
	private ArrayList<Tower> tower;

	private int level;
	
    Point start;

    Timer jobScheduler;
	MonsterRegenTimer regenTimer;
	
	public Game(int level) throws IOException, InterruptedException {
		this.level = level;
		init();
		setTimer();
	}
	
	public void setTimer() {
		jobScheduler = new Timer(true);

	    regenTimer = new MonsterRegenTimer(null, tileType);
	    jobScheduler.schedule(regenTimer, 100, 300/4);
	}
	
	public void cancelTimer() {
		for(int i=0; i<tower.size(); i++) {
			tower.get(i).cancelTimer();
		}
		for(int i=0; i<monster.length; i++) {
			if(monster[i] != null) {
				monster[i].cancelTimer();
			}
		}
	}
	
	public void buildTower(int x, int y, int type) {
		Tower t = tile[x][y].buildTower(type);
		tower.add(t);
	}
	
	public void init() throws IOException, InterruptedException {
		monster = new Monster[30];
		tower = new ArrayList<Tower>();

		tile = getMap();
	}
	
	public void drawMap(Graphics g) {
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j].drawTile(g);
		
		for(int i=0; i<monster.length; i++)
			if(monster[i] != null)
				monster[i].drawMonster(g);
	}
	
	public Tile[][] getMap() throws IOException, InterruptedException {
		return MapParser.parse(Main.c.loadMap(level).map);
	}
}