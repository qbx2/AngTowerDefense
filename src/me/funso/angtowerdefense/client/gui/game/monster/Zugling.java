package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Main;

import java.awt.*;
import java.io.IOException;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Zugling extends Monster {

    public Zugling() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[0];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("1", x-size_x/2, y+size_y/2);
    }
}
