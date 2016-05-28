package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class SurpriseBox extends Tower {

    public SurpriseBox(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y);

        cost = Main.towerInfo[8].cost;
        attack_range = size*Main.towerInfo[8].attack_range;
        attack_speed = Main.towerInfo[8].attack_speed;
        damage = Main.towerInfo[8].damage;
        name = Main.towerInfo[8].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("9", r_x, r_y);
    }
}
