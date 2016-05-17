package me.funso.angtowerdefense;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import me.funso.angtowerdefense.client.astar.Point;
import me.funso.angtowerdefense.client.gui.timer.MonsterRegenTimer;

public class Map {

	final int SIZE = 32;
	
	private char[][] tileType;
	private Tile[][] tile;
	
	public static Monster[] monster;
	private ArrayList<Tower> tower;

    Point start;

    Timer jobScheduler;
	MonsterRegenTimer regenTimer;
	
	public Map() throws IOException {
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
	
	public void init() throws IOException {
		tile = new Tile[SIZE][SIZE];
		tileType = new char[SIZE][SIZE];
		
		monster = new Monster[30];
		tower = new ArrayList<Tower>();

		getMap();

		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j] = new Tile(j,i,tileType[i][j]);
	}
	
	public void drawMap(Graphics g) {
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				tile[i][j].drawTile(g);
		
		for(int i=0; i<monster.length; i++)
			if(monster[i] != null)
				monster[i].drawMonster(g);
	}
	
	public void getMap() throws IOException {
		File file = new File("/Users/baek/Documents/workspace/AngTowerDefense/stage2.txt");
		FileInputStream input = new FileInputStream(file);
		byte buf[] = new byte[input.available()];
		input.read(buf);
		input.close();
		me.funso.angtowerdefense.client.astar.Map map = new me.funso.angtowerdefense.client.astar.Map(new String(buf));
		tileType = map.getMap();
	}
}
