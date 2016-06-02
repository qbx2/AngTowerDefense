package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class LazerTower extends Tower {

    public LazerTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 2);

        attack_range = size*Main.towerInfo[2].attack_range;
        attack_speed = Main.towerInfo[2].attack_speed;
        damage = Main.towerInfo[2].damage;
        name = Main.towerInfo[2].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("3", r_x, r_y);
    }
}
