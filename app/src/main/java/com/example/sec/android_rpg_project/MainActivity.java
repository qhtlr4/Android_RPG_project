package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    final static int GAME_SETTING = 0;
    DBHelper dbHelper;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);
    }

    public void newbtnOnClick(View v) {
        Init_User user = new Init_User();
        Intent intent = new Intent(this, GameActivity.class);

        intent.putExtra("level", user.level);
        intent.putExtra("exp", user.exp);
        intent.putExtra("currentHp", user.currentHp);
        intent.putExtra("maxHp", user.maxHp);
        intent.putExtra("currentMp", user.currentMp);
        intent.putExtra("maxMp", user.maxMp);
        intent.putExtra("gold", user.gold);
        intent.putExtra("attack", user.attack);
        intent.putExtra("defence", user.defence);
        intent.putExtra("addpoint", user.addpoint);

        str = "새 게임을 시작합니다.";
        make_toast(str);
        startActivityForResult(intent, GAME_SETTING);
        //finish();
        onPause();
    }
    public void loadbtnOnClick(View v) {
        SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
        if(user_status.getBoolean("exist_data", false) == false){
            str = "저장된 데이터가 없습니다.";
            make_toast(str);
            return;
        }
        Load_User user = new Load_User(user_status);
        Intent intent = new Intent(this, GameActivity.class);

        intent.putExtra("level", user.level);
        intent.putExtra("exp", user.exp);
        intent.putExtra("currentHp", user.currentHp);
        intent.putExtra("maxHp", user.maxHp);
        intent.putExtra("currentMp", user.currentMp);
        intent.putExtra("maxMp", user.maxMp);
        intent.putExtra("gold", user.gold);
        intent.putExtra("attack", user.attack);
        intent.putExtra("defence", user.defence);
        intent.putExtra("addpoint", user.addpoint);

        str = "정상적으로 불러왔습니다.";
        make_toast(str);

        onPause();
        startActivityForResult(intent, GAME_SETTING);
    }
    public void make_toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
