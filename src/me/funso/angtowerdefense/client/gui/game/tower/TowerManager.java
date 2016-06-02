package me.funso.angtowerdefense.client.gui.game.tower;

import me.funso.angtowerdefense.client.Main;
import me.funso.angtowerdefense.client.gui.game.GameMain;
import me.funso.angtowerdefense.client.gui.game.monster.Monster;
import me.funso.angtowerdefense.client.gui.game.monster.MonsterManager;

import java.util.ArrayList;

/**
 * Created by baek on 2016. 5. 27..
 */
public class TowerManager {

    public static ArrayList<Tower> towers;

    public TowerManager() {
        towers = new ArrayList<Tower>();
    }

    public Tower buildTower(int x, int y, int r_x, int r_y, int type) {
        switch (type) {
            case 0:
                towers.add(new FastTower(x, y, r_x, r_y));
                break;
            case 1:
                towers.add(new ThunderBolts(x, y, r_x, r_y));
                break;
            case 2:
                towers.add(new LazerTower(x, y, r_x, r_y));
                break;
            case 3:
                towers.add(new Swamp(x, y, r_x, r_y));
                break;
            case 4:
                towers.add(new MineralTower(x, y, r_x, r_y));
                break;
            case 5:
                towers.add(new BombTower(x, y, r_x, r_y));
                break;
            case 6:
                towers.add(new PoisonTower(x, y, r_x, r_y));
                break;
            case 7:
                towers.add(new FreezingTower(x, y, r_x, r_y));
                break;
            case 8:
                towers.add(new SurpriseBox(x, y, r_x, r_y));
                break;
            case 9:
                for(int i=MonsterManager.monsters.size()-1; i>=0; i--) {
                    if(MonsterManager.monsters.get(i) != null)
                        MonsterManager.monsters.get(i).attackWait();
                        if(MonsterManager.monsters.get(i).damaged(Main.towerInfo[9].damage)) {
                            MonsterManager.monsters.get(i).attackRelease();
                        } else {
                            if(MonsterManager.type.size() > i) {
                                GameMain.gm.earnMineral(MonsterManager.type.get(i));
                                MonsterManager.type.remove(i);
                            }
                            if(MonsterManager.monsters.size() > i) {
                                MonsterManager.monsters.remove(i);
                            }
                        }
                }
                return null;
            default:
        }

        return towers.get(towers.size()-1);
    }
}
