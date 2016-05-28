package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Nuclear extends Tower {

    public Nuclear(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y);

        cost = Main.towerInfo[9].cost;
        attack_range = size*Main.towerInfo[9].attack_range;
        attack_speed = Main.towerInfo[9].attack_speed;
        damage = Main.towerInfo[9].damage;
        name = Main.towerInfo[9].name;
        
        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("10", r_x, r_y);
    }
}