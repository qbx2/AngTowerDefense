package me.funso.angtowerdefense.client.gui.game.monster;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by baek on 2016. 5. 27..
 */
public class MonsterManager {

    public static ArrayList<Monster> monsters = new ArrayList<Monster>();
    public static char[][] tileType;

    public MonsterManager(char[][] tileType) {
        this.tileType = tileType;
    }

    public void regen(int type) throws IOException, InterruptedException {
        switch (type) {
            case 1:
                monsters.add(new Zugling());
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
            default:

        }
    }
}
