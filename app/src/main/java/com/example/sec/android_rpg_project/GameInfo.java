package com.example.sec.android_rpg_project;

/**
 * Created by USER on 2017-11-01.
 */

public class GameInfo{
    public int get_maxexp(int level) {
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
        }
        return 9990;
    }


}