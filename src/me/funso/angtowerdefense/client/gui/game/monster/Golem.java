package me.funso.angtowerdefense.client.gui.game.monster;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.timer.MoveTimer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

/**
 * Created by baek on 2016. 5. 27..
 */
public class Golem extends Monster {

    public Golem() throws IOException, InterruptedException {
        super();

        MonsterInfo info = Main.monsterInfo[1];
        hp = info.hp;
        armor = info.armor;
        speed = info.speed;
        name = info.name;

        setTimer();
    }

    public void paint(Graphics g) {
        if(size_x == 0) {
            if(image != null) {
                int imageHeight = image[1].getHeight(Main.frame);
                int imageWidth = image[1].getWidth(Main.frame);
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

        g.drawImage(image[1], x-size_x/2, y-size_y/2, size_x, size_y, Main.frame);
    }
}
