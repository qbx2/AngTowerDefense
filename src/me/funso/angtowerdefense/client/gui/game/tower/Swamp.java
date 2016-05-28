package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Swamp extends Tower {

    public Swamp(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y);

        cost = Main.towerInfo[3].cost;
        attack_range = size*Main.towerInfo[3].attack_range;
        attack_speed = Main.towerInfo[3].attack_speed;
        damage = Main.towerInfo[3].damage;
        name = Main.towerInfo[3].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("4", r_x, r_y);
    }
}
