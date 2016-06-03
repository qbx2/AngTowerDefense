package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Citizen extends Monster {

    public Citizen() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[2];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        if(image[2] != null) {
            int imageHeight = image[2].getHeight(Main.frame);
            int imageWidth = image[2].getWidth(Main.frame);
            if (imageHeight < imageWidth) {
                size_x = Device.dim.height / 70;
                size_y = size_x * imageHeight / imageWidth;
            } else {
                size_y = Device.dim.height / 70;
                size_x = size_y * imageWidth / imageHeight;
            }
        }
        randMoveCalc();

        setTimer();
    }

    public void paint(Graphics g) {
        g.drawImage(image[2], x-size_x/2, y-size_y/2, size_x, size_y, Main.frame);
    }
}
