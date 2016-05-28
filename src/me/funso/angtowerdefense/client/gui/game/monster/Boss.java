package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Main;

import java.awt.*;
import java.io.IOException;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Boss extends Monster {

    public Boss() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[5];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("6", x-size_x/2, y+size_y/2);
    }
}
