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
        currentMp = 1;
        maxMp = 1;
        gold = 0;
        attack = 5;
        defence = 5;
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
                return 30;
            case 2:
                return 70;
            case 3:
                return 110;
            case 4:
                return 170;
            case 5:
                return 250;
            case 6:
                return 370;
            case 7:
                return 520;
            case 8:
                return 730;
            case 9:
                return 940;
            case 10:
                return 1150;
            case 11:
                return 2500;
            case 12:
                return 3300;
            case 13:
                return 4000;
        }
        return 9990;
    }

}
