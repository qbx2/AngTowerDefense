package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class WindTower extends Tower {

    public WindTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 7);

        attack_range = size* Main.towerInfo[7].attack_range;
        attack_speed = Main.towerInfo[7].attack_speed;
        damage = Main.towerInfo[7].damage;
        name = Main.towerInfo[7].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("8", r_x, r_y);
    }
}
