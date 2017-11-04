package com.example.sec.android_rpg_project;

/**
 * Created by USER on 2017-11-01.
 */

public class Init_User{
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

    public Init_User(){
        level = "1";
        exp = "0";
        currentHp = "50";
        maxHp = "50";
        currentMp = "30";
        maxMp = "30";
        gold = "200";
        attack = "3";
        defence = "0";
        addpoint = "5";
    }
}

class Inventory{
    int iten_id;
    String item_name;
}

class Item{
    int item_id;
    int cost;
    String item_name;
    int use_level;
}

class Equipment extends Item{
    int attack;
    int defence;

    Equipment(int item_id, String item_name, int use_level, int cost, int attack, int defence){
        this.item_id = item_id;
        this.item_name = item_name;
        this.use_level = use_level;
        this.cost = cost;
        this.attack = attack;
        this.defence = defence;
    }
}

class Potion extends Item{
    int addHp;
    int addMp;

    Potion(int item_id, String item_name, int cost, int addHp, int addMp){
        this.item_id = item_id;
        this.item_name = item_name;
        this.cost = cost;
        this.addHp = addHp;
        this.addMp = addMp;
    }
}