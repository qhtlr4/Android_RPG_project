package com.example.sec.android_rpg_project;

import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by USER on 2017-11-16.
 */

public class User implements Serializable{
    int level;
    int exp;
    int currentHp;
    int maxHp;
    int currentMp;
    int maxMp;
    int gold;
    int attack;
    int defence;
    int addpoint;

    public User(){
        level = 1;
        exp = 0;
        currentHp = 50;
        maxHp = 50;
        currentMp = 30;
        maxMp = 30;
        gold = 2000;
        attack = 3;
        defence = 0;
        addpoint = 5;
    }

    public User(SharedPreferences sp){
        level = sp.getInt("level", 0);
        exp = sp.getInt("exp",0);
        currentHp = sp.getInt("currentHp", 0);
        maxHp = sp.getInt("maxHp", 0);
        currentMp = sp.getInt("currentMp", 0);
        maxMp = sp.getInt("maxMp", 0);
        gold = sp.getInt("gold", 0);
        attack = sp.getInt("attack", 0);
        defence = sp.getInt("defence", 0);
        addpoint = sp.getInt("addpoint", 0);
    }
    public User(int level, int exp, int currentHp, int maxHp, int currentMp, int maxMp, int gold, int attack, int defence, int addpoint){
        this.level = level;
        this.exp = exp;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.currentMp = currentMp;
        this.maxMp = maxMp;
        this.gold = gold;
        this.attack = attack;
        this.defence = defence;
        this.addpoint = addpoint;
    }
    public int get_maxexp() {
        switch (level) {
            case 1:
                return 10;
            case 2:
                return 30;
            case 3:
                return 50;
            case 4:
                return 80;
            case 5:
                return 120;
            case 6:
                return 160;
            case 7:
                return 210;
            case 8:
                return 260;
            case 9:
                return 330;
            case 10:
                return 1000;
            case 11:
                return 1000;
        }
        return 9990;
    }

}
