package me.funso.angtowerdefense;


import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import me.funso.angtowerdefense.client.Device;

public class Tile {
	
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

	public Tower buildTower(int tower_type) {
		if(type == TileType.ROAD || tower != null) {
			return null;
		} else {
			tower = new Tower(x, y, r_x, r_y, tower_type);
		}
		return tower;
	}
	
	public void drawTile(Graphics g) {
		if(type == TileType.NORMAL) {
			g.drawRect(r_x-size/2, r_y-size/2, size, size);
		}
		if(tower != null) {
			tower.drawTower(g);
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
