package me.funso.angtowerdefense;

import java.io.UnsupportedEncodingException;

public class MapParser {
	public static Tile[][] parse(String strMap) throws UnsupportedEncodingException {
		Tile ret[][] = new Tile[32][32];
		int x = 0;
		int y = 0;
		for(byte b: strMap.getBytes("UTF-8")) {
			TileType type = TileType.NORMAL;
			
			if(b == 'S') {
				type = TileType.START;
			} else if(b == 'G') {
				type = TileType.GOAL;
			} else if(b == '_') {
				
			} else if(b == '\n') {
				x = 0;
				y++;
				continue;
			} else if(b == '0') {
				type = TileType.ROAD;
			} else {
				//ignore
				continue;
			}
			
			Tile t = new Tile(x, y, type);
			ret[x][y] = t;
			x++;
			
		}
		return ret;
	}
}
