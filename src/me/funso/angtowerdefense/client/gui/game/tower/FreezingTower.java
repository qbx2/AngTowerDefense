package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class FreezingTower extends Tower {

    public FreezingTower(int x, int y, int r_x, int r_y) {
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

    public void attack() {

        int monster_x, monster_y;

        for(int i = 0; i< MonsterManager.monsters.size(); i++) {
            if(MonsterManager.monsters.get(i) != null) {
                monster_x = MonsterManager.monsters.get(i).getX();
                monster_y = MonsterManager.monsters.get(i).getY();
                if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
                    MonsterManager.monsters.get(i).isFreeze = true;
                }
            }
        }
    }
}
