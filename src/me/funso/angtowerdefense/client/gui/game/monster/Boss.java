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
public class Boss extends Monster {

    public Boss() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[5];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        if(image[5] != null) {
            int imageHeight = image[5].getHeight(Main.frame);
            int imageWidth = image[5].getWidth(Main.frame);
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
        g.drawImage(image[5], x-size_x/2, y-size_y/2, size_x, size_y, Main.frame);
    }
}
