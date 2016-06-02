package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.Game;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.awt.*;

/**
 * Created by baek on 2016. 5. 27..
 */
public class ThunderBolts extends Tower {

    public ThunderBolts(int x, int y, int r_x, int r_y) {
        super(x, y, r_x, r_y, 1);

        attack_range = size*Main.towerInfo[1].attack_range;
        attack_speed = Main.towerInfo[1].attack_speed;
        damage = Main.towerInfo[1].damage;
        name = Main.towerInfo[1].name;

        setTimer();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawString("2", r_x, r_y);
    }

    public void attack() {

        int monster_x, monster_y;

        for(int i=MonsterManager.monsters.size()-1; i>=0; i--) {
            monster_x = MonsterManager.monsters.get(i).getX();
            monster_y = MonsterManager.monsters.get(i).getY();
            if(attack_range >= Math.sqrt(Math.pow(monster_x-r_x, 2) + Math.pow(monster_y-r_y, 2))) {
                MonsterManager.monsters.get(i).attackWait();
                if(MonsterManager.monsters.get(i).damaged(damage)) {
                    MonsterManager.monsters.get(i).attackRelease();
                } else {	//die
                    if(MonsterManager.type.size() > i) {
                        GameMain.gm.earnMineral(MonsterManager.type.get(i));
                        MonsterManager.type.remove(i);
                    }
                    if(MonsterManager.monsters.size() > i) {
                        MonsterManager.monsters.remove(i);
                    }
                    kill++;
                }
            }
        }
    }
}
