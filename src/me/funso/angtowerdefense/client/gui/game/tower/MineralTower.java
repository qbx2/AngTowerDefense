package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class MineralTower extends Tower {

    public MineralTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 4);

        attack_range = Device.dim.height*Main.towerInfo[4].attack_range/40;
        attack_speed = Main.towerInfo[4].attack_speed;
        damage = Main.towerInfo[4].damage;
        name = Main.towerInfo[4].name;

        int imageHeight = image[4].getHeight(Main.frame);
        int imageWidth = image[4].getWidth(Main.frame);
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
        g.drawImage(image[4], r_x-size_x/2, r_y-size_y/2, size_x, size_y, Main.frame);
    }

    public void attack() {
        GameMain.gm.earnMineral(damage);
    }
}
