package me.funso.angtowerdefense;

import java.io.Serializable;

public class StageInfo implements Serializable {
	private static final long serialVersionUID = 1835636836455048403L;
	public int idx;
	public int map_idx;
	public String gen_rule;
	public int global_mobbuff;
	public int reward_gold;
	public int reward_exp;
	
	public StageInfo(int idx, int map_idx, String gen_rule, int global_mobbuff, int reward_gold, int reward_exp) {
		this.idx = idx;
		this.map_idx = map_idx;
		this.gen_rule = gen_rule;
		this.global_mobbuff = global_mobbuff;
		this.reward_gold = reward_gold;
		this.reward_exp = reward_exp;
	}
}
