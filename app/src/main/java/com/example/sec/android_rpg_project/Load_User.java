package com.example.sec.android_rpg_project;

import android.content.SharedPreferences;

/**
 * Created by USER on 2017-11-01.
 */

public class Load_User{
    String level;
    String exp;
    String currentHp;
    String maxHp;
    String currentMp;
    String maxMp;
    String gold;
    String attack;
    String defence;
    String addpoint;

    public Load_User(SharedPreferences sp){
        level = sp.getString("level", "0");
        exp = sp.getString("exp","0");
        currentHp = sp.getString("currentHp", "0");
        maxHp = sp.getString("maxHp", "0");
        currentMp = sp.getString("currentMp", "0");
        maxMp = sp.getString("maxMp", "0");
        gold = sp.getString("gold", "0");
        attack = sp.getString("attack", "0");
        defence = sp.getString("defence", "0");
        addpoint = sp.getString("addpoint", "0");
    }
    public Load_User(String level, String exp, String currentHp, String maxHp, String currentMp, String maxMp, String gold, String attack, String defence, String addpoint){
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
}
