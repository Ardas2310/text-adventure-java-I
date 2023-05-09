package game;

import game.utility.GameManager;
import game.Character;
import java.io.Serializable;
import java.util.Objects;


public class Enemies implements Serializable {


    private static GameManager instance;
    private int enemiesHP;
    private int enemiesDamage;

    public int getEnemiesDamage() {
        return enemiesDamage;
    }

    public Enemies(int enemiesHP, int enemiesDamage) {
        this.enemiesHP = enemiesHP;
        this.enemiesDamage = enemiesDamage;
    }

    public int getHit(int enemiesDamage) {
        enemiesHP = enemiesHP - enemiesDamage;
        return enemiesHP;
    }

    public int getEnemiesHP() {
        return enemiesHP;
    }

}

