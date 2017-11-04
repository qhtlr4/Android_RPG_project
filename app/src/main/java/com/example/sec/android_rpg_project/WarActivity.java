package com.example.sec.android_rpg_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.sec.android_rpg_project.MainActivity.GAME_SETTING;

public class WarActivity extends Activity {

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
    TextView enemy_current_hp;      //몬스터 실시간 체력바 정보
    TextView enemy_max_hp;

    String str; //toast에 사용할 문자열

    Random rand = new Random();     //몬스터 공격시 크리티컬 계산을 위한 random클래스

    HashMap<String, String> hashMap = new HashMap<String, String>();      //드롭 아이템을 검사하여 인벤토리에 저장

    GameInfo gameInfo = new GameInfo(); //게임에 설정된 최대 경험치를 가져오기위한 클래스 객체

    Enemy enemy = new Enemy();      //적 정보
    ImageView enemy_image;
    Drawable[] drawables = new Drawable[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_war);

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

        level_txt.setText(intent.getStringExtra("level"));
        exp_txt.setText(intent.getStringExtra("exp"));
        currentHp_txt.setText(intent.getStringExtra("currentHp"));
        maxHp_txt.setText(intent.getStringExtra("maxHp"));
        currentMp_txt.setText(intent.getStringExtra("currentMp"));
        maxMp_txt.setText(intent.getStringExtra("maxMp"));
        gold_txt.setText(intent.getStringExtra("gold"));
        attack_txt.setText(intent.getStringExtra("attack"));
        defence_txt.setText(intent.getStringExtra("defence"));
        addpoint_txt.setText(intent.getStringExtra("addpoint"));
        attack = Integer.parseInt(attack_txt.getText().toString());
        defence = Integer.parseInt(defence_txt.getText().toString());
        addpoint = Integer.parseInt(addpoint_txt.getText().toString());

        now_exp = Integer.parseInt(exp_txt.getText().toString());
        limit_exp = gameInfo.get_maxexp(Integer.parseInt(level_txt.getText().toString()));
        setExp_bar(now_exp, limit_exp);

        enemy = enemy.getEnemy(dbHelper.get_enemy(Integer.parseInt(level_txt.getText().toString())));   //적 정보를 db에서 불러와서 enemy객체에 저장

        user_current_hp = (TextView)findViewById(R.id.user_current_hp);
        user_max_hp = (TextView)findViewById(R.id.user_max_hp);
        enemy_current_hp = (TextView)findViewById(R.id.enemy_current_hp);
        enemy_max_hp = (TextView)findViewById(R.id.enemy_max_hp);

        user_current_hp.setText(currentHp_txt.getText().toString());
        user_max_hp.setText(maxHp_txt.getText().toString());
        enemy_current_hp.setText(String.valueOf(enemy.hp));
        enemy_max_hp.setText(String.valueOf(enemy.hp));

        if(SDK_INT >=22) {
            drawables[0] = getResources().getDrawable(R.drawable.mob_lv1, null);
            drawables[1] = getResources().getDrawable(R.drawable.mob_lv2, null);
            drawables[2] = getResources().getDrawable(R.drawable.mob_lv1, null);
            drawables[3] = getResources().getDrawable(R.drawable.mob_lv1, null);
            drawables[4] = getResources().getDrawable(R.drawable.mob_lv1, null);
        } else {
            drawables[0] = getResources().getDrawable(R.drawable.mob_lv1);
            drawables[1] = getResources().getDrawable(R.drawable.mob_lv2);
            drawables[2] = getResources().getDrawable(R.drawable.mob_lv1);
            drawables[3] = getResources().getDrawable(R.drawable.mob_lv1);
            drawables[4] = getResources().getDrawable(R.drawable.mob_lv1);
        }
        enemy.setImage(drawables);
        enemy_image = (ImageView)findViewById(R.id.enemy_view);
        enemy_image.setImageDrawable(enemy.getImage());
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
                gold_txt.setText(String.valueOf(Integer.parseInt(gold_txt.getText().toString())-((int)(Integer.parseInt(gold_txt.getText().toString())*0.2))));
                int minus_exp = (int)(limit_exp * 0.2);
                now_exp -= minus_exp;
                if(now_exp<=0){
                    exp_txt.setText("0");
                }
                else
                    exp_txt.setText(String.valueOf(Integer.parseInt(exp_txt.getText().toString())-minus_exp));
                make_toast(String.valueOf((int)(Integer.parseInt(gold_txt.getText().toString())*0.2)) + "골드를 잃었습니다.");
                moveActivity();
                finish();
                break;
            case R.id.potion:
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
            currentHp_txt.setText(String.valueOf(Integer.parseInt(currentHp_txt.getText().toString()) - (enemy.damage-Integer.parseInt(defence_txt.getText().toString()))));
            user_current_hp.setText(currentHp_txt.getText().toString());
            if(Integer.parseInt(user_current_hp.getText().toString()) <= 0){
                int minus_exp = (int)(limit_exp * 0.5);
                exp_txt.setText(String.valueOf(Integer.parseInt(exp_txt.getText().toString())-minus_exp));
                if(Integer.parseInt(exp_txt.getText().toString()) < 0){
                    exp_txt.setText("0");
                }
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
                limit_exp = gameInfo.get_maxexp(Integer.parseInt(level_txt.getText().toString()));
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
        Load_User user = new Load_User(level_txt.getText().toString(), exp_txt.getText().toString(), currentHp_txt.getText().toString(), maxHp_txt.getText().toString(), currentMp_txt.getText().toString(), maxMp_txt.getText().toString(), gold_txt.getText().toString(), attack_txt.getText().toString(), defence_txt.getText().toString(), addpoint_txt.getText().toString());
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

}
