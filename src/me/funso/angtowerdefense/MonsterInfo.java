package me.funso.angtowerdefense;

import java.io.Serializable;

public class MonsterInfo implements Serializable {
	private static final long serialVersionUID = 7569673736914614375L;
	public int idx;
	public String name;
	public String description;
	public int hp;
	public int armor;
	public int speed;
	
	public MonsterInfo(int idx, String name, String description, int hp, int armor, int speed) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.hp = hp;
		this.armor = armor;
		this.speed = speed;
	}
}
