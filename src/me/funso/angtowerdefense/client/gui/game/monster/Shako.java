package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Main;

import java.io.IOException;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Shako extends Monster {

    public Shako() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.c.monsterInfo(4).info;
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }
}
