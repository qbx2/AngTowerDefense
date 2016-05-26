package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.gui.game.GameMain;

/**
 * Created by baek on 2016. 5. 27..
 */
public class WindTower extends Tower {

    public WindTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y);

        cost = GameMain.towerInfo[7].cost;
        attack_range = size*GameMain.towerInfo[7].attack_range;
        attack_speed = GameMain.towerInfo[7].attack_speed;
        damage = GameMain.towerInfo[7].damage;
        name = GameMain.towerInfo[7].name;

        setTimer();
    }
}
