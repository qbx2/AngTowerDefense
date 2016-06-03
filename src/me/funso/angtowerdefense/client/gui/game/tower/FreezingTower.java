package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class FreezingTower extends Tower {

    public FreezingTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 7);

        attack_range = Device.dim.height*Main.towerInfo[7].attack_range/40;
        attack_speed = Main.towerInfo[7].attack_speed;
        damage = Main.towerInfo[7].damage;
        name = Main.towerInfo[7].name;

        int imageHeight = image[7].getHeight(Main.frame);
        int imageWidth = image[7].getWidth(Main.frame);
        if (imageHeight < imageWidth) {
            size_x = Device.dim.height / 40;
            size_y = size_x * imageHeight / imageWidth;
        } else {
            size_y = Device.dim.height / 40;
            size_x = size_y * imageWidth / imageHeight;
        }

        setTimer();
    }

    public void paint(Graphics g) {
        g.drawImage(image[7], r_x-size_x/2, r_y-size_y/2, size_x, size_y, Main.frame);
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
