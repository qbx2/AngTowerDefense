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
public class Bee extends Monster {

    public Bee() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[4];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }

    public void paint(Graphics g) {
        if(size_x == 0) {
            if(image != null) {
                int imageHeight = image[4].getHeight(Main.frame);
                int imageWidth = image[4].getWidth(Main.frame);
                if (imageHeight < imageWidth) {
                    size_x = Device.dim.height / 70;
                    size_y = size_x * imageHeight / imageWidth;
                } else {
                    size_y = Device.dim.height / 70;
                    size_x = size_y * imageWidth / imageHeight;
                }
            }
            randMoveCalc();
        }

        g.drawImage(image[4], x-size_x/2, y-size_y/2, size_x, size_y, Main.frame);
    }
}
