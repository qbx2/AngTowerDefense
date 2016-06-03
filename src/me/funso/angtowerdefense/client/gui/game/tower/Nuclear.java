package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Nuclear extends Tower {

    public Nuclear(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 9);

        attack_range = Device.dim.height*Main.towerInfo[9].attack_range/40;
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
