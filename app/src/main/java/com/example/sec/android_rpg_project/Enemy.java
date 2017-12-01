package com.example.sec.android_rpg_project;

import android.graphics.drawable.Drawable;

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
        if(is_boss == 0){
            image = drawable[mob_num - 1];
        }
        else if(is_boss == 1){
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
}