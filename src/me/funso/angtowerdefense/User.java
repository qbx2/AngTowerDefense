package me.funso.angtowerdefense;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 5759045437396168704L;
	
	public String user_id;
	public String nickname;
	public int level;
	public int exp;
	public int gold;
	
	public User(String user_id, String nickname, int level, int exp, int gold) {
		this.user_id = user_id;
		this.nickname = nickname;
		this.level = level;
		this.exp = exp;
		this.gold = gold;
	}
	
	public String toString() {
		return "User{user_id: " + user_id + ", nickname: " + nickname + ", level: " + level + ", exp: " + exp + ", gold: " + gold + "}";
	}
}
