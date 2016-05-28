package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class MineralTower extends Tower {

    public MineralTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 4);

        attack_range = size*Main.towerInfo[4].attack_range;
        attack_speed = Main.towerInfo[4].attack_speed;
        damage = Main.towerInfo[4].damage;
        name = Main.towerInfo[4].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("5", r_x, r_y);
    }
}
