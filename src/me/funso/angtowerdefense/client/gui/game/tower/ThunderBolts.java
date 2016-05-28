package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class ThunderBolts extends Tower {

    public ThunderBolts(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y);

        cost = Main.towerInfo[1].cost;
        attack_range = size*Main.towerInfo[1].attack_range;
        attack_speed = Main.towerInfo[1].attack_speed;
        damage = Main.towerInfo[1].damage;
        name = Main.towerInfo[1].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("2", r_x, r_y);
    }
}
