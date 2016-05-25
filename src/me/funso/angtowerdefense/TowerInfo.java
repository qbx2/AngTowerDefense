package me.funso.angtowerdefense;

import java.io.Serializable;

public class TowerInfo implements Serializable {
	private static final long serialVersionUID = -7907079758038303316L;
	public int idx;
	public String name;
	public String description;
	public int damage;
	public int attack_speed;
	public int unlock_level;
	public int cost;
	public int attack_range;
	
	public TowerInfo(int idx, String name, String description, int damage, int attack_speed, int unlock_level, int cost, int attack_range) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.damage = damage;
		this.unlock_level = unlock_level;
		this.cost = cost;
		this.attack_range = attack_range;
	}
}
