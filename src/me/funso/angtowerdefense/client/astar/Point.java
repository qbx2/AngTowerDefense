package me.funso.angtowerdefense.client.astar;

public class Point {
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public Point() {
		
	}
	
	@Override
	public boolean equals(Object a) {
		if(a instanceof Point) {
			Point tmp = (Point)a;
			return tmp.x == this.x && tmp.y == this.y;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
	}
	
	public String toString() {
		return "("+this.x+", "+this.y+")";
	}
}
