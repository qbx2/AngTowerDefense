package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Golem extends Monster {

    public Golem() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[1];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("2", x-size_x/2, y+size_y/2);
    }
}