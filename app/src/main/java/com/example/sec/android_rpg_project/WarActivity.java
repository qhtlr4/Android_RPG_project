package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.sec.android_rpg_project.MainActivity.GAME_SETTING;

public class WarActivity extends Activity {

    int add_attack;
    int add_defence;

    DBHelper dbHelper;
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
    TextView attack_txt;
    TextView defence_txt;
    TextView addpoint_txt;
    int attack;
    int defence;
    int addpoint;

    TextView user_current_hp;
    TextView user_max_hp;
    TextView user_current_mp;
    TextView user_max_mp;
    TextView enemy_current_hp;      //몬스터 실시간 체력바 정보
    TextView enemy_max_hp;

    ArrayList<String> potion_items;
    ArrayAdapter<String> potion_adaptor;
    ListView potion_view;
    LinearLayout battle_main_layout;
    LinearLayout inventory_layout;

    String str; //toast에 사용할 문자열

    Random rand = new Random();     //몬스터 공격시 크리티컬 계산을 위한 random클래스

    HashMap<String, String> hashMap = new HashMap<String, String>();      //드롭 아이템을 검사하여 인벤토리에 저장

    User user;

    Enemy enemy = new Enemy();      //적 정보
    TextView enemy_name;
    ImageView enemy_image;
    Drawable[] drawables = new Drawable[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_war);

        add_attack = dbHelper.change_equipment_item(1, -1, dbHelper.equipment_item(1)).get("attack");
        add_defence = dbHelper.change_equipment_item(2, -1, dbHelper.equipment_item(2)).get("defence");

        level_txt = (TextView)findViewById(R.id.level);
        exp_txt = (TextView)findViewById(R.id.exp);
        currentHp_txt = (TextView)findViewById(R.id.current_hp);
        currentMp_txt = (TextView)findViewById(R.id.current_mp);
        maxHp_txt = (TextView)findViewById(R.id.max_hp);
        maxMp_txt = (TextView)findViewById(R.id.max_mp);
        gold_txt = (TextView)findViewById(R.id.gold);
        exp_bar = (ProgressBar)findViewById(R.id.exp_bar);
        attack_txt = (TextView)findViewById(R.id.attack_value);
        defence_txt = (TextView)findViewById(R.id.defence_value);
        addpoint_txt = (TextView)findViewById(R.id.addpoint_value);

        Intent intent = getIntent();

        user = (User)intent.getSerializableExtra("user");

        level_txt.setText(String.valueOf(user.level));
        exp_txt.setText(String.valueOf(user.exp));
        currentHp_txt.setText(String.valueOf(user.currentHp));
        maxHp_txt.setText(String.valueOf(user.maxHp));
        currentMp_txt.setText(String.valueOf(user.currentMp));
        maxMp_txt.setText(String.valueOf(user.maxMp));
        gold_txt.setText(String.valueOf(user.gold));
        attack_txt.setText(String.valueOf(user.attack));
        defence_txt.setText(String.valueOf(user.defence));
        addpoint_txt.setText(String.valueOf(user.addpoint));
        attack = Integer.parseInt(attack_txt.getText().toString());
        defence = Integer.parseInt(defence_txt.getText().toString());
        addpoint = Integer.parseInt(addpoint_txt.getText().toString());

        now_exp = Integer.parseInt(exp_txt.getText().toString());
        limit_exp = user.get_maxexp();
        setExp_bar(now_exp, limit_exp);

        enemy = enemy.getEnemy(dbHelper.get_enemy(intent.getIntExtra("difficulty_min", 1), intent.getIntExtra("difficulty_max", 1), intent.getIntExtra("is_boss", 0)));   //적 정보를 db에서 불러와서 enemy객체에 저장

        user_current_hp = (TextView)findViewById(R.id.user_current_hp);
        user_max_hp = (TextView)findViewById(R.id.user_max_hp);
        user_current_mp = (TextView)findViewById(R.id.user_current_mp);
        user_max_mp = (TextView)findViewById(R.id.user_max_mp);
        enemy_current_hp = (TextView)findViewById(R.id.enemy_current_hp);
        enemy_max_hp = (TextView)findViewById(R.id.enemy_max_hp);

        user_current_hp.setText(currentHp_txt.getText().toString());
        user_max_hp.setText(maxHp_txt.getText().toString());
        user_current_mp.setText(currentMp_txt.getText().toString());
        user_max_mp.setText(maxMp_txt.getText().toString());
        enemy_current_hp.setText(String.valueOf(enemy.hp));
        enemy_max_hp.setText(String.valueOf(enemy.hp));

        battle_main_layout = (LinearLayout)findViewById(R.id.battle_main_layout);
        inventory_layout = (LinearLayout)findViewById(R.id.inventory_layout);
        potion_view = (ListView)findViewById(R.id.potion_view);

        if(SDK_INT >= 22) {
            drawables[0] = getResources().getDrawable(R.drawable.mob_lv1, null);
            drawables[1] = getResources().getDrawable(R.drawable.mob_lv2, null);
            drawables[2] = getResources().getDrawable(R.drawable.mob_lv3, null);
            drawables[3] = getResources().getDrawable(R.drawable.mob_lv4, null);
            drawables[4] = getResources().getDrawable(R.drawable.mob_lv2, null);
            drawables[5] = getResources().getDrawable(R.drawable.mob_lv4, null);
            drawables[6] = getResources().getDrawable(R.drawable.mob_lv2, null);
            drawables[7] = getResources().getDrawable(R.drawable.mob_lv4, null);
            drawables[8] = getResources().getDrawable(R.drawable.mob_lv2, null);
            drawables[9] = getResources().getDrawable(R.drawable.mob_lv4, null);
            drawables[10] = getResources().getDrawable(R.drawable.boss_1, null);
        } else {
            drawables[0] = getResources().getDrawable(R.drawable.mob_lv1);
            drawables[1] = getResources().getDrawable(R.drawable.mob_lv2);
            drawables[2] = getResources().getDrawable(R.drawable.mob_lv3);
            drawables[3] = getResources().getDrawable(R.drawable.mob_lv4);
            drawables[4] = getResources().getDrawable(R.drawable.mob_lv1);
            drawables[5] = getResources().getDrawable(R.drawable.mob_lv4);
            drawables[6] = getResources().getDrawable(R.drawable.mob_lv2);
            drawables[7] = getResources().getDrawable(R.drawable.mob_lv4);
            drawables[8] = getResources().getDrawable(R.drawable.mob_lv2);
            drawables[9] = getResources().getDrawable(R.drawable.mob_lv4);
            drawables[10] = getResources().getDrawable(R.drawable.boss_1);
        }
        enemy.setImage(drawables);
        enemy_image = (ImageView)findViewById(R.id.enemy_view);
        enemy_image.setImageDrawable(enemy.getImage());
        enemy_name = (TextView)findViewById(R.id.enemy_name);
        enemy_name.setText(enemy.name);
    }

    public int rand(int max) {
        int rand_num;
        rand_num = rand.nextInt(max) + 1;
        return rand_num;
    }

    public void battle_action(View v){
        int id = v.getId();
        switch (id){
            case R.id.enemy_view:
                battle();
                break;
            case R.id.escape:
                int loss_gold = (int)(Integer.parseInt(gold_txt.getText().toString())*0.2);
                gold_txt.setText(String.valueOf(Integer.parseInt(gold_txt.getText().toString())-loss_gold));
                make_toast(String.valueOf(loss_gold) + "골드를 잃었습니다.");
                moveActivity();
                finish();
                break;
            case R.id.potion:
                battle_main_layout.setVisibility(View.INVISIBLE);
                inventory_layout.setVisibility(View.VISIBLE);
                potion_items = dbHelper.getInventoryResult(3);
                potion_adaptor = new ArrayAdapter<String>(WarActivity.this, android.R.layout.simple_list_item_single_choice, potion_items);
                potion_view.setAdapter(potion_adaptor);
                break;
            case R.id.skill:
                break;
        }
    }

    public void battle(){
        //크리티컬 판단을 위한 식
        int critical_rate = 10;
        //적 hp 감소
        if(critical_rate >= rand(100)){
            //크리티컬
            str = "크리티컬 !! ";
            enemy.hp = enemy.hp - ((int)(attack*1.3));
            enemy_current_hp.setText(String.valueOf(enemy.hp));
        }
        else {
            str = "공격";
            enemy.hp -= attack;
            enemy_current_hp.setText(String.valueOf(enemy.hp));
        }

        if(enemy.hp > 0) {
            make_toast(str);
            // enemy의 action
            int hurt;
            if( enemy.damage <= Integer.parseInt(defence_txt.getText().toString())){
                hurt = 1;
            }
            else
                hurt = enemy.damage - Integer.parseInt(defence_txt.getText().toString());
            currentHp_txt.setText(String.valueOf(Integer.parseInt(currentHp_txt.getText().toString()) - hurt));
            user_current_hp.setText(currentHp_txt.getText().toString());
            if(Integer.parseInt(user_current_hp.getText().toString()) <= 0){
                str = "사망하였습니다.";
                make_toast(str);
                int minus_exp = (int)(limit_exp * 0.1);
                exp_txt.setText(String.valueOf(Integer.parseInt(exp_txt.getText().toString())-minus_exp));
                if(Integer.parseInt(exp_txt.getText().toString()) < 0){
                    str = "경험치 " + (Integer.parseInt(exp_txt.getText().toString()) * (-1)) + "\n";
                    exp_txt.setText("0");
                }
                else
                    str = "경험치 " + (minus_exp * (-1)) + "\n";

                int minus_gold = (int)(Integer.parseInt(gold_txt.getText().toString())*0.4);
                gold_txt.setText(String.valueOf(Integer.parseInt(gold_txt.getText().toString())-minus_gold));
                str += String.valueOf(minus_gold) + "골드를 잃었습니다.";
                make_toast(str);
                user_current_hp.setText("0");
                currentHp_txt.setText("10");        //사망시 hp10으로 복구
                //GameActivity로 이동
                moveActivity();
            }
        }
        else if (enemy.hp <= 0){
            make_toast("적 처치 !");
            enemy_current_hp.setText("0");
            exp_txt.setText(String.valueOf(Integer.parseInt(exp_txt.getText().toString()) + enemy.exp));
            now_exp += enemy.exp;
            if(now_exp >= limit_exp){
                make_toast("레벨업");
                level_txt.setText(String.valueOf(Integer.parseInt(level_txt.getText().toString()) + 1));
                exp_txt.setText(String.valueOf(Integer.parseInt(exp_txt.getText().toString()) - limit_exp));
                now_exp = now_exp-limit_exp;
                maxHp_txt.setText(String.valueOf(Integer.parseInt(maxHp_txt.getText().toString()) + 3));
                maxMp_txt.setText(String.valueOf(Integer.parseInt(maxMp_txt.getText().toString()) + 1));
                attack_txt.setText(String.valueOf(Integer.parseInt(attack_txt.getText().toString()) + 1));
                defence_txt.setText(String.valueOf(Integer.parseInt(defence_txt.getText().toString()) + 1));
                addpoint_txt.setText(String.valueOf(Integer.parseInt(addpoint_txt.getText().toString()) + 5));
                user.level++;
                limit_exp = user.get_maxexp();
                setExp_bar(now_exp, limit_exp);
            }
            else{
                setExp_bar(now_exp, limit_exp);
            }
            hashMap = dbHelper.get_item(enemy);
            make_toast(hashMap.get("result"));

            if(hashMap.get("gold") != null)
                gold_txt.setText(String.valueOf(Integer.parseInt(gold_txt.getText().toString())+Integer.parseInt(hashMap.get("gold"))));

            //GameActivity로 이동
            moveActivity();
        }
    }

    public void moveActivity(){
        User user = new User(Integer.parseInt(level_txt.getText().toString()), Integer.parseInt(exp_txt.getText().toString()), Integer.parseInt(currentHp_txt.getText().toString()), Integer.parseInt(maxHp_txt.getText().toString()),
                Integer.parseInt(currentMp_txt.getText().toString()), Integer.parseInt(maxMp_txt.getText().toString()), Integer.parseInt(gold_txt.getText().toString()),
                Integer.parseInt(String.valueOf(Integer.parseInt(attack_txt.getText().toString())-add_attack)), Integer.parseInt(String.valueOf(Integer.parseInt(defence_txt.getText().toString())-add_defence)),
                Integer.parseInt(addpoint_txt.getText().toString()));
        Intent intent = getIntent();
        intent.putExtra("user", user);

        setResult(GAME_SETTING, intent);
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
                battle_main_layout.setVisibility(View.VISIBLE);
                return true;
            }
            else {
                Button escape = (Button) findViewById(R.id.escape);
                battle_action(escape);
                moveActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
