package me.funso.angtowerdefense.client.astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Map {
	
	private char map[][] = new char[32][32];
	//;
	
	public Map(String map) {
		char[] tmp = map.toCharArray();
		int y = 0;
		int i = -1;
		
		while(i < tmp.length - 1) {
			int x = 0;

			while(i < tmp.length - 1) {
				i++;

				if(tmp[i] != 'S' && tmp[i] != 'G' && tmp[i] != '_' && tmp[i] != '0' && tmp[i] != '\n') {
					continue;
				}
				
				if(tmp[i] == '\n') {
					break;
				}
				
				this.map[y][x] = tmp[i];
				x++;
			}
			
			y++;
		}

		for(y=0; y < 32; y++) {
			for(int x = 0; x < 32; x++) {
				System.out.print(this.map[y][x]);
			}
			System.out.println();
		}
	}
	
	public char[][] getMap() {
		return map;
	}
	
	private double h(Point start, Point goal) {
		//Euclidean distance
		return (start.x - goal.x)*(start.x - goal.x) + (start.y - goal.y)*(start.y - goal.y);
	}
	
	public String reconstruct_path(HashMap<Point, Point> cameFrom,Point current){
		//Queue<Point> total_path  = new LinkedList<Point>();
		//total_path.add(current);
		StringBuilder sb = new StringBuilder();
		
		while(cameFrom.containsKey(current)) {
			Point tcurrent = cameFrom.get(current);
			//total_path.add(current);
			int dx = current.x - tcurrent.x;
			int dy = current.y - tcurrent.y;
			char c;
			if(dx == 0) {
				if(dy == -1) {
					c = 'u';
				} else {
					c = 'd';
				}
			} else {
				if(dx == -1) {
					c = 'l';
				} else {
					c = 'r';
				}
			}
			sb.append(c);
			current = tcurrent;
		}

		return sb.reverse().toString();
	}	
	
	public Point find(char ch) {
		for(int y = 0; y < 32; y++) {
			for(int x = 0; x < 32; x++) {
				if(this.map[y][x] == ch) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}
	
	public String aStar(Point start, Point goal)
	{
		System.out.println(start + " to " + goal);
		ArrayList<Point> closedSet = new ArrayList<Point>();
		Queue<Point> openSet = new LinkedList<Point>();
		openSet.add(start);
		
		HashMap<Point, Point> cameFrom = new HashMap<Point, Point>();
		HashMap<Point, Double> gScore = new HashMap<Point, Double>();
		gScore.put(start, 0.);
		HashMap<Point, Double> fScore = new HashMap<Point, Double>();
		fScore.put(start, h(start, goal));
		
		while(!openSet.isEmpty()){
			double minFScore = -1;
			ArrayList<Point> minCurrents = null;
			
			for(Point temp: openSet) {
				double tmpFScore = fScore.get(temp);
				if(minFScore == -1 || tmpFScore < minFScore) {
					minCurrents = new ArrayList<Point>();
					minCurrents.add(temp);
					minFScore = tmpFScore;
				} else if(tmpFScore == minFScore) {
					minCurrents.add(temp);
				}
			}
			
			Point current = minCurrents.get(new Random().nextInt(minCurrents.size()));
			
			if(current.equals(goal))
				return reconstruct_path(cameFrom, current);
			
			openSet.remove(current);
			closedSet.add(current);
			
			Point [] neighbors = new Point[4];
			for(int i=0; i < 4; i++){
				neighbors[i] = new Point();
				int dx=0, dy=0;
				switch(i) {
				case 0:
					dx = -1;
					dy = 0;
					break;
				case 1:
					dx = 1;
					dy = 0;
					break;
				case 2:
					dx = 0;
					dy = -1;
					break;
				case 3:
					dx = 0;
					dy = 1;
					break;
				}
				neighbors[i].x = current.x+dx;
				neighbors[i].y = current.y+dy;
			}
			
			for(Point neighbor: neighbors){
				if(!checkAvailability(neighbor)) {
					closedSet.add(neighbor);
				}
				
				if(closedSet.contains(neighbor)) {
					continue;
				}
				
				double tentative_gScore = gScore.get(current) + 1;

				if(!openSet.contains(neighbor)) {
					openSet.add(neighbor);
				} else if(tentative_gScore >= gScore.get(neighbor)) {
					continue;
				}
				
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentative_gScore);
				fScore.put(neighbor, tentative_gScore + h(neighbor, goal));
			}
		}
		
		return null;
	}

	private boolean checkAvailability(Point neighbor) {
		if(neighbor.x < 0 || neighbor.x >= this.map[0].length || neighbor.y < 0 || neighbor.y >= this.map.length) {
			return false;
		}
		return this.map[neighbor.y][neighbor.x] != '_';
	}
}
