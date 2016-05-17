package me.funso.angtowerdefense;


import java.awt.Graphics;

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
		calcXYSize();
	}

	public Tower buildTower(int tower_type) {
		if(type == TileType.BLOCKED || tower != null) {
			return null;
		} else {
			tower = new Tower(x, y, r_x, r_y, tower_type);
		}
		return tower;
	}
	
	public void drawTile(Graphics g) {
		if(type == TileType.BLOCKED) {
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
		case BLOCKED:
			size = Device.dim.height/35;
			break;
		//case '0': case 'S': case 'G':
		//	break;
		}
	}
}
