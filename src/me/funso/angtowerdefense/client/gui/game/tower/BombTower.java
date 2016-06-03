package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.MonsterInfo;
import me.funso.angtowerdefense.TowerInfo;
import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class BombTower extends Tower {

    public BombTower(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 5);

        attack_range = Device.dim.height*Main.towerInfo[5].attack_range/40;
        attack_speed = Main.towerInfo[5].attack_speed;
        damage = Main.towerInfo[5].damage;
        name = Main.towerInfo[5].name;

        int imageHeight = image[5].getHeight(Main.frame);
        int imageWidth = image[5].getWidth(Main.frame);
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
        g.drawImage(image[5], r_x-size_x/2, r_y-size_y/2, size_x, size_y, Main.frame);
    }

    public void attack() {

        int monster_x, monster_y;

        if(target != null) {
            monster_x = target.getX();
            monster_y = target.getY();
            if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
                for(int i=0; i<MonsterManager.monsters.size(); i++) {
                    if(MonsterManager.monsters.get(i) == target)
                        continue;
                    if(attack_range/3*2 >= Math.sqrt(Math.pow(monster_x-MonsterManager.monsters.get(i).getX(),2)
                            + Math.pow(monster_y-MonsterManager.monsters.get(i).getY(),2))) {
                        if(MonsterManager.monsters.get(i).damaged(damage/3)) {
                        } else {
                            if(MonsterManager.type.size() >= i) {
                                GameMain.gm.earnMineral(MonsterManager.type.get(i));
                                MonsterManager.type.remove(i);
                            }
                            if(MonsterManager.monsters.size() >= i) {
                                MonsterManager.monsters.remove(i);
                            }
                            kill++;
                        }
                    }
                }
                if(target.damaged(damage)) {		//didn't die
                } else {		//die
                    for(int i = 0; i< MonsterManager.monsters.size(); i++) {
                        if(MonsterManager.monsters.get(i) == target) {
                            if(MonsterManager.type.size() >= i) {
                                GameMain.gm.earnMineral(MonsterManager.type.get(i));
                                MonsterManager.type.remove(i);
                            }
                            if(MonsterManager.monsters.size() >= i) {
                                MonsterManager.monsters.remove(i);
                            }
                            target = null;
                            kill++;
                            return;
                        }
                    }
                }
                return;
            }
        }

        for(int i=0; i<MonsterManager.monsters.size(); i++) {
            if(MonsterManager.monsters.get(i) != null) {
                monster_x = MonsterManager.monsters.get(i).getX();
                monster_y = MonsterManager.monsters.get(i).getY();
                if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
                    target = MonsterManager.monsters.get(i);
                    for(int j=0; j<MonsterManager.monsters.size(); j++) {
                        if(MonsterManager.monsters.get(j) == target)
                            continue;
                        if(attack_range/3*2 >= Math.sqrt(Math.pow(monster_x-MonsterManager.monsters.get(j).getX(),2)
                                + Math.pow(monster_y-MonsterManager.monsters.get(j).getY(),2))) {
                            MonsterManager.monsters.get(j).attackWait();
                            if(MonsterManager.monsters.get(j).damaged(damage/3)) {
                                MonsterManager.monsters.get(j).attackRelease();
                            } else {
                                if(MonsterManager.type.size() > j) {
                                    GameMain.gm.earnMineral(MonsterManager.type.get(j));
                                    MonsterManager.type.remove(j);
                                }
                                if(MonsterManager.monsters.size() > j) {
                                    MonsterManager.monsters.remove(j);
                                }
                                kill++;
                            }

                        }
                    }
                    target.attackWait();
                    if(target.damaged(damage)) {
                        target.attackRelease();
                    } else {	//die
                        if(MonsterManager.type.size() > i) {
                            GameMain.gm.earnMineral(MonsterManager.type.get(i));
                            MonsterManager.type.remove(i);
                        }
                        if(MonsterManager.monsters.size() > i) {
                            MonsterManager.monsters.remove(i);
                        }
                        kill++;
                        target = null;
                    }
                    return;
                }
            }
        }

        target = null;
    }
}
