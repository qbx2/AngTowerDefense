package me.funso.angtowerdefense.client.gui.game.tower;

import java.util.ArrayList;

/**
 * Created by baek on 2016. 5. 27..
 */
public class TowerManager {

    public static ArrayList<Tower> towers = new ArrayList<Tower>();

    public TowerManager() {

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
                towers.add(new WindTower(x, y, r_x, r_y));
                break;
            case 8:
                towers.add(new SurpriseBox(x, y, r_x, r_y));
                break;
            case 9:
                towers.add(new Nuclear(x, y, r_x, r_y));
                break;
            default:
        }

        return towers.get(towers.size()-1);
    }
}
