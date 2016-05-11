package me.funso.angtowerdefense;

import java.io.UnsupportedEncodingException;

public class MapParser {
	public static Tile[][] parse(String strMap) throws UnsupportedEncodingException {
		Tile ret[][] = new Tile[32][32];
		int x = 0;
		int y = 0;
		for(byte b: strMap.getBytes("UTF-8")) {
			Tile t = new Tile();
			
			if(b == 'S') {
				t.type = TileType.START;
			} else if(b == 'G') {
				t.type = TileType.GOAL;
			} else if(b == '_') {
				t.type = TileType.BLOCKED;
			} else if(b == '\n') {
				x = 0;
				y++;
				continue;
			} else if(b == '0') {
				
			} else {
				continue;
			}
			
			ret[x][y] = t;
			x++;
			
		}
		return ret;
	}
}
