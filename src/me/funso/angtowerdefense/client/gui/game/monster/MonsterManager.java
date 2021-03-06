package me.funso.angtowerdefense.client.gui.game.monster;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by baek on 2016. 5. 27..
 */
public class MonsterManager {

    public static ArrayList<Monster> monsters;
    public static ArrayList<Integer> type;

    public MonsterManager() {
        monsters = new ArrayList<Monster>();
        type = new ArrayList<Integer>();
    }

    public void regen(int type) throws IOException, InterruptedException {
        switch (type) {
            case 1:
                monsters.add(new Zergling());
                break;
            case 2:
                monsters.add(new Golem());
                break;
            case 3:
                monsters.add(new Citizen());
                break;
            case 4:
                monsters.add(new Shako());
                break;
            case 5:
                monsters.add(new Bee());
                break;
            case 6:
                break;
            default:

        }
        this.type.add(type);
    }
}
