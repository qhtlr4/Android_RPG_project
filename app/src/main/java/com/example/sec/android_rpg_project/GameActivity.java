package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sec.android_rpg_project.MainActivity.GAME_SETTING;

public class GameActivity extends Activity {
    final DBHelper dbHelper = new DBHelper(this);

    TextView level_txt;
    TextView exp_txt;
    TextView currentHp_txt;
    TextView maxHp_txt;
    TextView currentMp_txt;
    TextView maxMp_txt;
    TextView gold_txt;
    int now_exp;
    int limit_exp;
    ProgressBar exp_bar;
    TextView max_hp;
    TextView max_mp;

    //inventory
    ArrayAdapter<String> weapon_adaptor;
    ArrayAdapter<String> armor_adaptor;
    ArrayAdapter<String> potion_adaptor;
    ArrayList<String> weapon_items;
    ArrayList<String> armor_items;
    ArrayList<String> potion_items;
    LinearLayout inventory_layout;
    ListView weapon_view;
    ListView armor_view;
    ListView potion_view;

    //shop
    ArrayAdapter<String> shop_adaptor;
    ArrayList<String> shop_items;
    ListView shop_view;

    //ability
    LinearLayout ability_layout;
    TextView ability_attack;
    int add_attack;
    TextView ability_defence;
    int add_defence;
    TextView ability_point;
    TextView ability_hp;
    TextView ability_mp;

    TableLayout menuBtns;
    LinearLayout war_level_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        level_txt = (TextView)findViewById(R.id.level);
        exp_txt = (TextView)findViewById(R.id.exp);
        currentHp_txt = (TextView)findViewById(R.id.current_hp);
        currentMp_txt = (TextView)findViewById(R.id.current_mp);
        maxHp_txt = (TextView)findViewById(R.id.max_hp);
        maxMp_txt = (TextView)findViewById(R.id.max_mp);
        gold_txt = (TextView)findViewById(R.id.gold);
        exp_bar = (ProgressBar)findViewById(R.id.exp_bar);
        ability_attack = (TextView)findViewById(R.id.ability_attack);
        ability_defence = (TextView)findViewById(R.id.ability_defence);
        ability_hp = (TextView)findViewById(R.id.ability_max_hp);
        ability_mp = (TextView)findViewById(R.id.ability_max_mp);
        ability_point = (TextView)findViewById(R.id.ability_point);
        war_level_Layout = (LinearLayout)findViewById(R.id.war_level_Layout);

        Intent intent = getIntent();

        level_txt.setText(intent.getStringExtra("level"));
        exp_txt.setText(intent.getStringExtra("exp"));
        currentHp_txt.setText(intent.getStringExtra("currentHp"));
        maxHp_txt.setText(intent.getStringExtra("maxHp"));
        currentMp_txt.setText(intent.getStringExtra("currentMp"));
        maxMp_txt.setText(intent.getStringExtra("maxMp"));
        gold_txt.setText(intent.getStringExtra("gold"));
        ability_hp.setText(maxHp_txt.getText());
        ability_mp.setText(maxMp_txt.getText());
        add_attack = dbHelper.change_equipment_item(1, -1, dbHelper.equipment_item(1)).get("attack");
        ability_attack.setText(String.valueOf(Integer.parseInt(intent.getStringExtra("attack")) + add_attack));
        add_defence = dbHelper.change_equipment_item(2, -1, dbHelper.equipment_item(2)).get("defence");
        ability_defence.setText(String.valueOf(Integer.parseInt(intent.getStringExtra("defence")) + add_defence));
        ability_point.setText(intent.getStringExtra("addpoint"));
        now_exp = Integer.parseInt(exp_txt.getText().toString());
        GameInfo gameInfo = new GameInfo();
        limit_exp = gameInfo.get_maxexp(Integer.parseInt(level_txt.getText().toString()));
        setExp_bar(now_exp, limit_exp);

        menuBtns = (TableLayout)findViewById(R.id.menuBtns);
        inventory_layout = (LinearLayout)findViewById(R.id.inventory_layout);
        weapon_view = (ListView)findViewById(R.id.weapon_view);
        armor_view = (ListView)findViewById(R.id.armor_view);
        potion_view = (ListView)findViewById(R.id.potion_view);
        Button inventory = (Button)findViewById(R.id.inventory);
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventory_layout.setVisibility(View.VISIBLE);
                menuBtns.setVisibility(View.INVISIBLE);

                weapon_items = new ArrayList<String>();
                armor_items = new ArrayList<String>();
                potion_items = new ArrayList<String>();

                weapon_items = dbHelper.getInventoryResult(1);
                armor_items = dbHelper.getInventoryResult(2);
                potion_items = dbHelper.getInventoryResult(3);

                weapon_adaptor = new ArrayAdapter<String>(GameActivity.this, android.R.layout.simple_list_item_single_choice, weapon_items);
                armor_adaptor = new ArrayAdapter<String>(GameActivity.this, android.R.layout.simple_list_item_single_choice, armor_items);
                potion_adaptor = new ArrayAdapter<String>(GameActivity.this, android.R.layout.simple_list_item_single_choice, potion_items);

                weapon_view.setAdapter(weapon_adaptor);
                weapon_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                if(dbHelper.equipment_item(1) != -1) {
                    weapon_view.setItemChecked(dbHelper.equipment_item(1) - 1, true);
                }
                armor_view.setAdapter(armor_adaptor);
                armor_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                if(dbHelper.equipment_item(2) != -1) {
                    armor_view.setItemChecked(dbHelper.equipment_item(2)-1, true);
                }
                potion_view.setAdapter(potion_adaptor);
            }
        });

        weapon_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                if(dbHelper.equipment_item(1) != -1) {
                    hashMap = dbHelper.change_equipment_item(1, dbHelper.equipment_item(1), i + 1);
                    ability_attack.setText(String.valueOf(Integer.parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(Integer.parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                else{
                    hashMap = dbHelper.change_equipment_item(1,-1,i+1);
                    ability_attack.setText(String.valueOf(Integer.parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(Integer.parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                make_toast("attack " + hashMap.get("attack") + "\ndefence" + hashMap.get("defence"));
            }
        });

        armor_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                if(dbHelper.equipment_item(2) != -1) {
                    hashMap = dbHelper.change_equipment_item(2, dbHelper.equipment_item(2), i + 1);
                    ability_attack.setText(String.valueOf(Integer.parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(Integer.parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                else{
                    hashMap = dbHelper.change_equipment_item(2,-1,i+1);
                    ability_attack.setText(String.valueOf(Integer.parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(Integer.parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                make_toast("attack " + hashMap.get("attack") + "\ndefence " + hashMap.get("defence"));
            }
        });

        shop_view = (ListView)findViewById(R.id.shop_view);
        Button shop = (Button)findViewById(R.id.shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shop_view.setVisibility(View.VISIBLE);
                menuBtns.setVisibility(View.INVISIBLE);
            }
        });

        shop_items = new ArrayList<String>();
        shop_items = dbHelper.getShopInfo();
        shop_adaptor = new ArrayAdapter<String>(GameActivity.this, android.R.layout.simple_list_item_1, shop_items);
        shop_view.setAdapter(shop_adaptor);

        ability_layout = (LinearLayout)findViewById(R.id.abilityLayout);
        Button ability = (Button)findViewById(R.id.ability);
        ability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ability_layout.setVisibility(View.VISIBLE);
                menuBtns.setVisibility(View.INVISIBLE);
            }
        });

        Button savebtn = (Button)findViewById(R.id.save_data);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
                SharedPreferences.Editor edit = user_status.edit();
                String level = level_txt.getText().toString();
                String exp = exp_txt.getText().toString();
                String currentHp = currentHp_txt.getText().toString();
                String maxHp = maxHp_txt.getText().toString();
                String currentMp = currentMp_txt.getText().toString();
                String maxMp = maxMp_txt.getText().toString();
                String gold = gold_txt.getText().toString();
                String attack = String.valueOf(Integer.parseInt(ability_attack.getText().toString()) - add_attack);
                String defence = String.valueOf(Integer.parseInt(ability_defence.getText().toString()) - add_defence);
                String addpoint = ability_point.getText().toString();

                edit.putString("level", level);
                edit.putString("exp", exp);
                edit.putString("currentHp", currentHp);
                edit.putString("currentMp", currentMp);
                edit.putString("maxHp", maxHp);
                edit.putString("maxMp", maxMp);
                edit.putString("gold", gold);
                edit.putString("attack", attack);
                edit.putString("defence", defence);
                edit.putString("addpoint", addpoint);
                edit.putBoolean("exist_data", true);
                edit.commit();
                String str = "저장되었습니다.";
                make_toast(str);
            }
        });
    }

    public void itemclass(View v){
        switch (v.getId()){
            case R.id.weapon_btn:
                weapon_view.setVisibility(View.VISIBLE);
                armor_view.setVisibility(View.INVISIBLE);
                potion_view.setVisibility(View.INVISIBLE);
                break;
            case R.id.armor_btn:
                weapon_view.setVisibility(View.INVISIBLE);
                armor_view.setVisibility(View.VISIBLE);
                potion_view.setVisibility(View.INVISIBLE);
                break;
            case R.id.potion_btn:
                weapon_view.setVisibility(View.INVISIBLE);
                armor_view.setVisibility(View.INVISIBLE);
                potion_view.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void ability_up(View v){
        if(Integer.parseInt(ability_point.getText().toString()) == 0){
            make_toast("더이상 남은 포인트가 없습니다.");
            return;
        }
        else{
            switch (v.getId()){
                case R.id.add_attack:
                    ability_attack.setText(String.valueOf(Integer.parseInt(ability_attack.getText().toString())+1));
                    ability_point.setText(String.valueOf(Integer.parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_defence:
                    ability_defence.setText(String.valueOf(Integer.parseInt(ability_defence.getText().toString())+1));
                    ability_point.setText(String.valueOf(Integer.parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_hp:
                    maxHp_txt.setText(String.valueOf(Integer.parseInt(maxHp_txt.getText().toString())+5));
                    ability_hp.setText(maxHp_txt.getText());
                    ability_point.setText(String.valueOf(Integer.parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_mp:
                    maxMp_txt.setText(String.valueOf(Integer.parseInt(maxMp_txt.getText().toString())+3));
                    ability_mp.setText(maxMp_txt.getText());
                    ability_point.setText(String.valueOf(Integer.parseInt(ability_point.getText().toString())-1));
                    break;
            }
        }
    }

    public void level_select(View v){
        war_level_Layout.setVisibility(View.VISIBLE);
        menuBtns.setVisibility(View.INVISIBLE);
    }

    public void warClick(View v) {
        Load_User user = new Load_User(level_txt.getText().toString(), exp_txt.getText().toString(), currentHp_txt.getText().toString(), maxHp_txt.getText().toString(), currentMp_txt.getText().toString(), maxMp_txt.getText().toString(), gold_txt.getText().toString(), ability_attack.getText().toString(), ability_defence.getText().toString(), ability_point.getText().toString());
        Intent intent = new Intent(this, WarActivity.class);
        int id = v.getId();

        switch (id){
            case R.id.easy:
                if(Integer.parseInt(user.level) - 2 <= 0 ){
                    intent.putExtra("difficulty_min", Integer.parseInt(user.level));
                }
                else
                    intent.putExtra("difficulty_min", Integer.parseInt(user.level)-2);
                intent.putExtra("difficulty_max", Integer.parseInt(user.level)+1);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.normal:
                if(Integer.parseInt(user.level) - 1 <= 0 ){
                    intent.putExtra("difficulty_min", Integer.parseInt(user.level));
                }
                else
                    intent.putExtra("difficulty_min", Integer.parseInt(user.level)-1);
                intent.putExtra("difficulty_max", Integer.parseInt(user.level)+3);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.hard:
                intent.putExtra("difficulty_min", Integer.parseInt(user.level));
                intent.putExtra("difficulty_max", Integer.parseInt(user.level)+5);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.boss:
                intent.putExtra("difficulty_min", Integer.parseInt(user.level));
                intent.putExtra("difficulty_max", Integer.parseInt(user.level));
                intent.putExtra("is_boss", 1);
                break;
        }

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

        startActivityForResult(intent, GAME_SETTING);
        finish();
    }

    public void setExp_bar(int current_exp, int max_exp){
        exp_bar.setProgress(current_exp);
        exp_bar.setMax(max_exp);
    }

    public void make_toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(inventory_layout.getVisibility() == View.VISIBLE ){
                inventory_layout.setVisibility(View.INVISIBLE);
                menuBtns.setVisibility(View.VISIBLE);
            }
            else if (ability_layout.getVisibility() == View.VISIBLE){
                ability_layout.setVisibility(View.INVISIBLE);
                menuBtns.setVisibility(View.VISIBLE);
            }
            else if (war_level_Layout.getVisibility() == View.VISIBLE){
                war_level_Layout.setVisibility(View.INVISIBLE);
                menuBtns.setVisibility(View.VISIBLE);
            }
            else if (shop_view.getVisibility() == View.VISIBLE){
                shop_view.setVisibility(View.INVISIBLE);
                menuBtns.setVisibility(View.VISIBLE);
            }
            else{
                finish();
                //activity 종료 시켜서 자동으로 전 화면으로 이동
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
        //KEYCODE_BACK이 아니면 액티비티에서 조작
    }
}
