package me.funso.angtowerdefense;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public void writeObject(ObjectOutputStream oout) throws IOException {
		System.out.println("writeObject");
		oout.writeUTF(user_id);
		oout.writeUTF(nickname);
		oout.writeInt(level);
		oout.writeInt(exp);
		oout.writeInt(gold);
	}
	
	public void readObject(ObjectInputStream oin) throws IOException, ClassNotFoundException {
		System.out.println("readObject");
		user_id = oin.readUTF();
		nickname = oin.readUTF();
		level = oin.readInt();
		exp = oin.readInt();
		gold = oin.readInt();
	}
	
	public String toString() {
		return "User{user_id: " + user_id + ", nickname: " + nickname + ", level: " + level + ", exp: " + exp + ", gold: " + gold + "}";
	}
}
