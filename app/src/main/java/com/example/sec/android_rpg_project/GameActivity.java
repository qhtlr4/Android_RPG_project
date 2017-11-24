package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
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
import static com.example.sec.android_rpg_project.R.id.exp;
import static java.lang.Integer.parseInt;

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

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        level_txt = (TextView)findViewById(R.id.level);
        exp_txt = (TextView)findViewById(exp);
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
        user = (User)intent.getSerializableExtra("user");

        level_txt.setText(String.valueOf(user.level));
        exp_txt.setText(String.valueOf(user.exp));
        currentHp_txt.setText(String.valueOf(user.currentHp));
        maxHp_txt.setText(String.valueOf(user.maxHp));
        currentMp_txt.setText(String.valueOf(user.currentMp));
        maxMp_txt.setText(String.valueOf(user.maxMp));
        gold_txt.setText(String.valueOf(user.gold));
        ability_hp.setText(String.valueOf(maxHp_txt.getText()));
        ability_mp.setText(String.valueOf(maxMp_txt.getText()));
        add_attack = dbHelper.change_equipment_item(1, -1, dbHelper.equipment_item(1)).get("attack");
        ability_attack.setText(String.valueOf(user.attack + add_attack));
        add_defence = dbHelper.change_equipment_item(2, -1, dbHelper.equipment_item(2)).get("defence");
        ability_defence.setText(String.valueOf(user.defence + add_defence));
        ability_point.setText(String.valueOf(user.addpoint));
        now_exp = parseInt(exp_txt.getText().toString());
        limit_exp = user.get_maxexp();
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
                    armor_view.setItemChecked(dbHelper.equipment_item(2) - 1, true);
                }
                potion_view.setAdapter(potion_adaptor);
            }
        });

        weapon_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                if(dbHelper.equipment_item(1) != -1) {  //착용체크가 있었을때
                    hashMap = dbHelper.change_equipment_item(1, dbHelper.equipment_item(1), i+1);
                    ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                else{   //착용체크가 없었을때
                    hashMap = dbHelper.change_equipment_item(1, -1, i+1);
                    ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                make_toast("공격력 " + hashMap.get("attack") + "\n방어력 " + hashMap.get("defence"));
            }
        });

        weapon_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i+1;
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("판매알림")
                        .setMessage("선택된 아이템을 판매하겠습니까?")
                        .setNegativeButton("아니오",null)
                        .setPositiveButton("예", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String, Integer> hashMap = dbHelper.sell_item(index, 1);
                                ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) - hashMap.get("attack")));
                                ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) - hashMap.get("defence")));
                                gold_txt.setText(String.valueOf(parseInt(gold_txt.getText().toString()) + hashMap.get("cost")));
                                weapon_adaptor.notifyDataSetChanged();
                            }
                        })
                        .show();
                return false;
            }
        });

        armor_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                if(dbHelper.equipment_item(2) != -1) {
                    hashMap = dbHelper.change_equipment_item(2, dbHelper.equipment_item(2), i+1);
                    ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                else{
                    hashMap = dbHelper.change_equipment_item(2, -1, i+1);
                    ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) + hashMap.get("attack")));
                    ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) + hashMap.get("defence")));
                }
                make_toast("공격력 " + hashMap.get("attack") + "\n방어력 " + hashMap.get("defence"));
            }
        });

        weapon_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i+1;
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("판매알림")
                        .setMessage("선택된 아이템을 판매하겠습니까?")
                        .setNegativeButton("아니오",null)
                        .setPositiveButton("예", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String, Integer> hashMap = dbHelper.sell_item(index, 1);
                                ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString()) - hashMap.get("attack")));
                                ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString()) - hashMap.get("defence")));
                                gold_txt.setText(String.valueOf(parseInt(gold_txt.getText().toString()) + hashMap.get("cost")));
                                armor_adaptor.notifyDataSetChanged();
                            }
                        })
                        .show();
                return false;
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
        shop_adaptor = new ArrayAdapter<String>(GameActivity.this, android.R.layout.simple_list_item_single_choice, shop_items);
        shop_view.setAdapter(shop_adaptor);
        shop_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        shop_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int cost = dbHelper.getcost(i+1);
                final int index = i+1;
                if(parseInt(gold_txt.getText().toString()) >= cost) {
                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle("알림")
                            .setMessage("선택된 아이템을 구매하겠습니까?")
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            })
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    gold_txt.setText(String.valueOf(parseInt(gold_txt.getText().toString()) - cost));
                                    dbHelper.insert_item(index, "buy");
                                    make_toast("정상적으로 구매하였습니다.");
                                }
                            }).show();
                }
                else{
                    make_toast("금액이 부족합니다.");
                }
            }
        });

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
                int level = parseInt(level_txt.getText().toString());
                int exp = Integer.parseInt(exp_txt.getText().toString());
                int currentHp = Integer.parseInt(currentHp_txt.getText().toString());
                int maxHp = Integer.parseInt(maxHp_txt.getText().toString());
                int currentMp = Integer.parseInt(currentMp_txt.getText().toString());
                int maxMp = Integer.parseInt(maxMp_txt.getText().toString());
                int gold = Integer.parseInt(gold_txt.getText().toString());
                int attack = Integer.parseInt(ability_attack.getText().toString()) - add_attack;
                int defence = Integer.parseInt(ability_defence.getText().toString()) - add_defence;
                int addpoint = Integer.parseInt(ability_point.getText().toString());

                saveStatus(level, exp, currentHp, maxHp, currentMp, maxMp, gold, attack, defence, addpoint);
            }
        });
    }

    public void saveStatus(int level, int exp, int currentHp, int maxHp, int currentMp, int maxMp, int gold, int attack, int defence, int addpoint){

        SharedPreferences user_status = getSharedPreferences("user_status", Service.MODE_PRIVATE);
        SharedPreferences.Editor edit = user_status.edit();

        edit.putInt("level", level);
        edit.putInt("exp", exp);
        edit.putInt("currentHp", currentHp);
        edit.putInt("currentMp", currentMp);
        edit.putInt("maxHp", maxHp);
        edit.putInt("maxMp", maxMp);
        edit.putInt("gold", gold);
        edit.putInt("attack", attack);
        edit.putInt("defence", defence);
        edit.putInt("addpoint", addpoint);
        edit.putBoolean("exist_data", true);
        edit.commit();

        String str = "저장되었습니다.";
        make_toast(str);
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
        if(parseInt(ability_point.getText().toString()) == 0){
            make_toast("더이상 남은 포인트가 없습니다.");
            return;
        }
        else{
            switch (v.getId()){
                case R.id.add_attack:
                    ability_attack.setText(String.valueOf(parseInt(ability_attack.getText().toString())+1));
                    ability_point.setText(String.valueOf(parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_defence:
                    ability_defence.setText(String.valueOf(parseInt(ability_defence.getText().toString())+1));
                    ability_point.setText(String.valueOf(parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_hp:
                    maxHp_txt.setText(String.valueOf(parseInt(maxHp_txt.getText().toString())+5));
                    ability_hp.setText(maxHp_txt.getText());
                    ability_point.setText(String.valueOf(parseInt(ability_point.getText().toString())-1));
                    break;
                case R.id.add_mp:
                    maxMp_txt.setText(String.valueOf(parseInt(maxMp_txt.getText().toString())+3));
                    ability_mp.setText(maxMp_txt.getText());
                    ability_point.setText(String.valueOf(parseInt(ability_point.getText().toString())-1));
                    break;
            }
        }
    }

    public void level_select(View v){
        war_level_Layout.setVisibility(View.VISIBLE);
        menuBtns.setVisibility(View.INVISIBLE);
    }

    public void warClick(View v) {
        User user = new User(parseInt(level_txt.getText().toString()), parseInt(exp_txt.getText().toString()), parseInt(currentHp_txt.getText().toString()),
                parseInt(maxHp_txt.getText().toString()), parseInt(currentMp_txt.getText().toString()), parseInt(maxMp_txt.getText().toString()),
                parseInt(gold_txt.getText().toString()), parseInt(ability_attack.getText().toString()), parseInt(ability_defence.getText().toString()), parseInt(ability_point.getText().toString()));
        Intent intent = new Intent(this, WarActivity.class);
        war_level_Layout.setVisibility(View.INVISIBLE);
        menuBtns.setVisibility(View.VISIBLE);
        int id = v.getId();

        switch (id){
            case R.id.easy:
                if(user.level - 2 <= 0 ){
                    intent.putExtra("difficulty_min", user.level);
                }
                else
                    intent.putExtra("difficulty_min", user.level-2);
                intent.putExtra("difficulty_max", user.level+1);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.normal:
                if(user.level - 1 <= 0 ){
                    intent.putExtra("difficulty_min", user.level);
                }
                else
                    intent.putExtra("difficulty_min", user.level-1);
                intent.putExtra("difficulty_max", user.level+3);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.hard:
                intent.putExtra("difficulty_min", user.level);
                intent.putExtra("difficulty_max", user.level+5);
                intent.putExtra("is_boss", 0);
                break;
            case R.id.boss:
                intent.putExtra("difficulty_min", user.level);
                intent.putExtra("difficulty_max", user.level);
                intent.putExtra("is_boss", 1);
                break;
        }

        intent.putExtra("user", user);
        startActivityForResult(intent, GAME_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        user = (User)data.getSerializableExtra("user");
        level_txt.setText(String.valueOf(user.level));
        exp_txt.setText(String.valueOf(user.exp));
        currentHp_txt.setText(String.valueOf(user.currentHp));
        maxHp_txt.setText(String.valueOf(user.maxHp));
        currentMp_txt.setText(String.valueOf(user.currentMp));
        maxMp_txt.setText(String.valueOf(user.maxMp));
        gold_txt.setText(String.valueOf(user.gold));
        ability_hp.setText(maxHp_txt.getText());
        ability_mp.setText(maxMp_txt.getText());
        add_attack = dbHelper.change_equipment_item(1, -1, dbHelper.equipment_item(1)).get("attack");
        ability_attack.setText(String.valueOf(user.attack + add_attack));
        add_defence = dbHelper.change_equipment_item(2, -1, dbHelper.equipment_item(2)).get("defence");
        ability_defence.setText(String.valueOf(user.defence + add_defence));
        ability_point.setText(String.valueOf(user.addpoint));
        now_exp = parseInt(exp_txt.getText().toString());
        limit_exp = user.get_maxexp();
        setExp_bar(now_exp, limit_exp);

        super.onActivityResult(requestCode, resultCode, data);
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
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
