package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    final static int GAME_SETTING = 0;
    DBHelper dbHelper;
    Intent intent;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);
    }

    public void newbtnOnClick(View v) {
        SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
        intent = new Intent(this, GameActivity.class);
        if(user_status.getBoolean("exist_data", false) == true){
            new AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("저장된 데이터가 있습니다.\n데이터를 삭제하고 다시 하겠습니까?")
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbHelper.init_db();
                            User user = new User();
                            intent.putExtra("user", user);

                            SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
                            SharedPreferences.Editor edit = user_status.edit();
                            edit.putString("level", user.level);
                            edit.putString("exp", user.exp);
                            edit.putString("currentHp", user.currentHp);
                            edit.putString("currentMp", user.currentMp);
                            edit.putString("maxHp", user.maxHp);
                            edit.putString("maxMp", user.maxMp);
                            edit.putString("gold", user.gold);
                            edit.putString("attack", user.attack);
                            edit.putString("defence", user.defence);
                            edit.putString("addpoint", user.addpoint);
                            edit.putBoolean("exist_data", true);

                            edit.commit();
                            String str = "저장되었습니다.";
                            make_toast(str);
                            str = "새 게임을 시작합니다.";
                            make_toast(str);
                            startActivity(intent);
                            //finish();
                            onPause();
                        }
                    }).show();
        }
        else{
            User user = new User();
            intent.putExtra("user", user);

            str = "새 게임을 시작합니다.";
            make_toast(str);
            startActivity(intent);
            //finish();
            onPause();
        }

    }
    public void loadbtnOnClick(View v) {
        SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
        if(user_status.getBoolean("exist_data", false) == false){
            str = "저장된 데이터가 없습니다.";
            make_toast(str);
            return;
        }
        User user = new User(user_status);
        intent = new Intent(this, GameActivity.class);
        intent.putExtra("user", user);
        str = "정상적으로 불러왔습니다.";
        make_toast(str);

        onPause();
        startActivity(intent);
    }
    public void make_toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
