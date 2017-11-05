package com.example.sec.android_rpg_project;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Created by USER on 2017-11-02.
 */

public class Enemy {
    int mob_num;
    String name;
    int hp;
    int damage;
    int exp;
    int is_boss;
    Drawable image;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable[] drawable) {
        if(mob_num == 1 && is_boss == 0){
            image = drawable[0];
        }
        if(mob_num == 2 && is_boss == 0){
            image = drawable[1];
        }
        if(mob_num == 3 && is_boss == 0){
            image = drawable[2];
        }
        if(mob_num == 4 && is_boss == 0){
            image = drawable[3];
        }
        if(mob_num == 5 && is_boss == 0){
            image = drawable[4];
        }
        if(mob_num == 6 && is_boss == 0){
            image = drawable[5];
        }
        if(mob_num == 7 && is_boss == 0){
            image = drawable[6];
        }
        if(mob_num == 8 && is_boss == 0){
            image = drawable[7];
        }
        if(mob_num == 9 && is_boss == 0){
            image = drawable[8];
        }
        if(mob_num == 10 && is_boss == 0){
            image = drawable[9];
        }
        if(is_boss == 1){
            image = drawable[10];
        }
    }

    Enemy(){
        mob_num = 0;
        name = "";
        hp = 0;
        damage = 0;
        exp = 0;
    }

    public Enemy getEnemy(Enemy enemy){
        return enemy;
    }
    public HashMap<String, String> dieEnemy(HashMap<String, String> hashMap){
        return hashMap;
    }
}