package me.funso.angtowerdefense.client.gui.game;


import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import me.funso.angtowerdefense.TileType;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.gui.game.tower.Tower;
import me.funso.angtowerdefense.client.gui.game.tower.TowerManager;

public class Tile implements Paintable {
	
	private int r_x,r_y;		//tile's center xy
	private int size;			//xy size;
	private int x,y;			//index in map;
	private TileType type;
	
	private Tower tower;
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		tower = null;
		GraphicsEnvironment ge = 
		GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		if(!ge.isHeadless())
			calcXYSize();
	}

	public Tower buildTower(int towerType) {
		if(type == TileType.ROAD || tower != null) {
			return null;
		} else {
			tower = Game.towerManager.buildTower(x,y,r_x,r_y,towerType);
		}
		return tower;
	}
	
	public void paint(Graphics g) {
		if(type == TileType.NORMAL) {
			g.drawRect(r_x-size/2, r_y-size/2, size, size);
		}
		if(tower != null) {
			tower.paint(g);
		}
	}
	
	public void calcXYSize() {
		r_x = Device.dim.height/35*(x+1)+Device.dim.height/70/50 + Device.dim.height/70;
		r_y = Device.dim.height/35*(y+1)+Device.dim.height/100;
		switch(type) {
		case NORMAL:
			size = Device.dim.height/35;
			break;
		//case '0': case 'S': case 'G':
		//	break;
		}
	}
}
