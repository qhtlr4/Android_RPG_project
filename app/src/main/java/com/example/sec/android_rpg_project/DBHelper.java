package com.example.sec.android_rpg_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by USER on 2017-11-02.
 */

public class DBHelper extends SQLiteOpenHelper {
    Random rand = new Random();

    public static int DATABASE_VERSION = 1;

    //아이템 판매/사용시 idx를 참조하여 조작할수 있도록 idx를 정렬하기위함
    //다음 insert실행시 들어갈 위치를 지정
    public static int idx_weapon;
    public static int idx_armor;
    public static int idx_potion;

    public DBHelper(Context context){
        super(context, "MyDB.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        idx_weapon = 2;
        idx_armor = 2;
        idx_potion = 3;

        //아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 아이템클래스(0, 1, 2, 3)
        //item_id = 1부터 시작
        sqLiteDatabase.execSQL("CREATE TABLE ITEM (item_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, attack INTEGER, defence INTEGER, addHp INTEGER, addMp INTEGER, cost INTEGER, class INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '골드', 0, 0, 0, 0, 0, 0);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '목검', 5, 0, 0, 0, 500, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '청동검', 7, 0, 0, 0, 5000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '강철검', 9, 0, 0, 0, 50000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '다이아몬드검', 11, 0, 0, 0, 500000, 1);");

        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '낡은 옷', 0, 5, 0, 0, 500, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '가죽 옷', 0, 7, 0, 0, 5000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '청동 갑옷', 0, 9, 0, 0, 50000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '가벼운 강철 갑옷', 0, 11, 0, 0, 500000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '무적갑옷', 0, 9999, 0, 0, 5000000, 2);");

        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, 'HP 포션', 0, 0, 30, 0, 500, 3);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, 'MP 포션', 0, 0, 0, 10, 300, 3);");

        sqLiteDatabase.execSQL("CREATE TABLE MOB (mob_id INTEGER PRIMARY KEY AUTOINCREMENT, mob_name TEXT, hp INTEGER, damage INTEGER, exp INTEGER, is_boss INTEGER);");  //
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨1 몬스터', 25, 8, 3, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨2 몬스터', 35, 10, 7, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨3 몬스터', 45, 13, 11, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨4 몬스터', 50, 16, 17, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨5 몬스터', 55, 21, 25, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨6 몬스터', 75, 28, 37, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨7 몬스터', 99, 32, 52, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨8 몬스터', 116, 35, 73, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨9 몬스터', 148, 48, 94, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨10 몬스터', 153, 52, 115, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨11 몬스터', 170, 61, 250, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨12 몬스터', 200, 70, 330, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨13 몬스터', 240, 75, 400, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨14 몬스터', 270, 80, 500, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '제 1 보스', 300, 100, 700, 1);");

        //index, 아이템번호, 몬스터번호, 최소개수, 최대개수, 드롭률
        sqLiteDatabase.execSQL("CREATE TABLE DROP_ITEM (idx INTEGER PRIMARY KEY AUTOINCREMENT, item_id INTEGER, mob_id INTEGER, min INTEGER, max INTEGER, ratio INTEGER);");  //ratio -> 10 = 1%
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 1, 10, 20, 500);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 2, 20, 24, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 3, 30, 40, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 4, 40, 50, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 5, 60, 80, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 6, 80, 100, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 7, 110, 125, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 8, 130, 140, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 9, 140, 150, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 10, 150, 180, 500);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 11, 2000, 5000, 800);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 1, 0, 1, 100);");  //목검
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 2, 0, 1, 120);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 3, 0, 1, 130);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 4, 0, 1, 140);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 3, 0, 1, 80);");  //청동검
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 4, 0, 1, 90);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 5, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 6, 0, 1, 110);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 7, 0, 1, 120);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 8, 0, 1, 130);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 3, 9, 0, 1, 140);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 4, 6, 0, 1, 50);");  //강철검
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 4, 7, 0, 1, 60);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 4, 8, 0, 1, 70);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 4, 9, 0, 1, 80);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 4, 10, 0, 1, 100);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 6, 1, 0, 1, 100);");  //낡은 옷
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 6, 2, 0, 1, 110);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 6, 3, 0, 1, 120);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 6, 4, 0, 1, 130);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 6, 5, 0, 1, 140);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 7, 4, 0, 1, 130);"); //가죽 옷
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 7, 5, 0, 1, 150);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 7, 6, 0, 1, 170);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 7, 7, 0, 1, 190);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 8, 7, 0, 1, 130);"); //청동갑옷
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 8, 8, 0, 1, 150);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 8, 9, 0, 1, 170);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 8, 10, 0, 1, 190);");

        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 1, 0, 1, 100);"); //포션
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 2, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 3, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 4, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 5, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 6, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 7, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 8, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 9, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 10, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 11, 11, 0, 1, 1000);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 1, 0, 1, 100);"); //포션
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 2, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 3, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 4, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 5, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 6, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 7, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 8, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 9, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 10, 0, 1, 100);");
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 12, 11, 0, 1, 1000);");

        // 샵위치아이템번호, 아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 종류
        sqLiteDatabase.execSQL("CREATE TABLE SHOP (idx INTEGER PRIMARY KEY AUTOINCREMENT, item_id INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 2);");    //목검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 3);");    //청동검

        // test
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 4);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 5);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 6);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 7);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 8);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 9);");    //청동검
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 10);");    //청동검

        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 11);");   //hp포션
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 12);");   //mp포션

        // 아이템idx, 인벤토리idx, 이름, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_1 (slot INTEGER PRIMARY KEY AUTOINCREMENT, idx INTEGER, enhance INTEGER, item_name TEXT, attack INTEGER, defence INTEGER, cost INTEGER, is_equip INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_2 (slot INTEGER PRIMARY KEY AUTOINCREMENT, idx INTEGER, enhance INTEGER, item_name TEXT, attack INTEGER, defence INTEGER, cost INTEGER, is_equip INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_3 (slot INTEGER PRIMARY KEY AUTOINCREMENT, idx INTEGER, item_name TEXT, addHp INTEGER, addMp INTEGER, cost INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격

        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 1, 0, '기본무기', 2, 0, 1, 1);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_2 VALUES(null, 1, 0, '기본방어구', 0, 2, 1, 1);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_3 VALUES(null, 1, '약한 HP 포션', 20, 0, 500);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_3 VALUES(null, 2, '약한 MP 포션', 0, 5, 500);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //저장데이터 삭제 후 초기화
    public void init_db(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS ITEM");
        db.execSQL("DROP TABLE IF EXISTS MOB");
        db.execSQL("DROP TABLE IF EXISTS DROP_ITEM");
        db.execSQL("DROP TABLE IF EXISTS SHOP");
        db.execSQL("DROP TABLE IF EXISTS INVENTORY_1");
        db.execSQL("DROP TABLE IF EXISTS INVENTORY_2");
        db.execSQL("DROP TABLE IF EXISTS INVENTORY_3");
        onCreate(db);
    }

    //shop에서 판매하는 항목을 return
    public ArrayList<String> getShopInfo(){
        ArrayList<String> result_array = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        //아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 아이템클래스(0, 1, 2, 3)
        Cursor cursor = db.rawQuery("SELECT * FROM ITEM, SHOP WHERE (ITEM.item_id = SHOP.item_id);", null);
        while (cursor.moveToNext()) {
            if(cursor.getInt(7) == 1 || cursor.getInt(7) == 2)
                result += "이름 : " + cursor.getString(1)
                        + "\n공격력 : " + cursor.getInt(2)
                        + "\t\t\t방어력 : " + cursor.getInt(3)
                        + "\t\t\t가격 : " + cursor.getInt(6);
            else
                result += "이름 : " + cursor.getString(1)
                        + "\t\t\t\tHP회복량 : " + cursor.getInt(4)
                        + "\t\t\t\tMP회복량 : " + cursor.getInt(5)
                        + "\n가격 : " + cursor.getInt(6);
            result_array.add(result);
            result = "";
        }
        return result_array;
    }

    public int getcost(int idx){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cost FROM ITEM WHERE item_id=(SELECT item_id FROM SHOP WHERE idx=" +idx + ");", null);

        if(cursor.moveToFirst() != false) {
            cursor.moveToFirst();
            int cost = cursor.getInt(0);
            cursor.close();
            return cost;
        }
        else
            return 99999;
    }

    //아이템 종류에 따라 인벤토리 1, 2, 3 항목을 return
    public ArrayList<String> getInventoryResult(int clas) {
        ArrayList<String> result_array = new ArrayList<String>();

        // 인벤토리 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String a = "";

        if(clas == 1){
            a = "_1";
        }
        else if(clas == 2){
            a = "_2";
        }
        else{
            a = "_3";
        }

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM INVENTORY" + a + ";", null);
        int enhance;
        while (cursor.moveToNext()) {
            enhance = cursor.getInt(2);
            if(clas == 1 || clas == 2) {
                if(enhance != 0)
                    result += "이름 : " + cursor.getString(3) + " (+ " + enhance
                            + ")\n공격력 : " + cursor.getInt(4)
                            + "\t\t\t방어력 : " + cursor.getInt(5)
                            + "\t\t\t가격 : " + (int) (cursor.getInt(6) / 2);
                else
                    result += "이름 : " + cursor.getString(3)
                            + "\n공격력 : " + cursor.getInt(4)
                            + "\t\t\t방어력 : " + cursor.getInt(5)
                            + "\t\t\t가격 : " + (int) (cursor.getInt(6) / 2);
            }
            else
                result += "이름 : " + cursor.getString(2)
                        + "\t\t\t\tHP회복량 : " + cursor.getInt(3)
                        + "\t\t\t\tMP회복량 : " + cursor.getInt(4)
                        + "\n가격 : " + (int)(cursor.getInt(5)/2);
            result_array.add(result);
            result = "";
        }
        return result_array;
    }

    //user가 호출한 것에 맞는 몬스터 정보를 return
    public Enemy get_enemy(int min_level, int max_level, int is_boss){
        SQLiteDatabase db = getReadableDatabase();
        int level;
        Cursor cursor = null;

        if(is_boss == 0) {
            level = start_rand(max_level, min_level);
            cursor = db.rawQuery("SELECT * FROM MOB WHERE mob_id=" + level, null);
        }
        else{
            cursor = db.rawQuery("SELECT * FROM MOB WHERE is_boss=" + is_boss, null);
        }
        Enemy enemy = new Enemy();
        while (cursor.moveToNext()) {
            enemy.mob_num = cursor.getInt(0);
            enemy.name = cursor.getString(1);
            enemy.hp = cursor.getInt(2);
            enemy.damage = cursor.getInt(3);
            enemy.exp = cursor.getInt(4);
            enemy.is_boss = cursor.getInt(5);
        }
        return enemy;
    }

    //전투가 끝난 후 gold, 아이템 획득정보를 return, 아이템은 insert_item()을 통하여 db에 저장
    public HashMap<String, String> get_item(Enemy enemy){
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String result = "";
        int drop;
        int count = 0;
        int gold = 0;
        //index, 아이템번호, 몬스터번호, 최소개수, 최대개수, 드롭률

        Cursor cursor = db.rawQuery("SELECT * FROM DROP_ITEM WHERE mob_id= " + enemy.mob_num, null);
        while (cursor.moveToNext()) {
            drop = start_rand(1000);

            int item_id = cursor.getInt(1);
            int min = cursor.getInt(3);
            int max = cursor.getInt(4);
            int ratio = cursor.getInt(5);

            if(drop <= ratio){           //드롭되었다.
                if(item_id == 1){          //골드인경우
                    drop = start_rand(max, min);
                    gold = drop;
                    result = drop + "골드와 ";
                }
                else {                          //아이템인 경우
                    hashMap.put("gold", "0");
                    insert_item(item_id, "get");
                    count++;
                }
            }
        }
        hashMap.put("gold", String.valueOf(gold));
        result = result + "아이템 "+ count +"개를 얻었다.";
        hashMap.put("result", result);

        return hashMap;
    }

    //전리품을 db에 저장 하기위한 함수, type = get
    //shop에서 구매한 아이템을 db에 저장, type = buy
    public void insert_item(int param_item_id, String type) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db2 = getWritableDatabase();
        Cursor cursor = null;
        if(type == "get") {
            cursor = db.rawQuery("SELECT * FROM ITEM WHERE item_id=" + param_item_id, null);
            while (cursor.moveToNext()) {
                String item_name = cursor.getString(1);
                int attack = cursor.getInt(2);
                int defence = cursor.getInt(3);
                int hp = cursor.getInt(4);
                int mp = cursor.getInt(5);
                int cost = cursor.getInt(6);
                int clas = cursor.getInt(7);
                if(clas == 1) {
                    db2.execSQL("INSERT INTO INVENTORY_1 VALUES(null, " + idx_weapon + ", "+ 0 + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                    idx_weapon++;
                }
                else if(clas == 2) {
                    db2.execSQL("INSERT INTO INVENTORY_2 VALUES(null, " + idx_armor + ", " + 0 + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                    idx_armor++;
                }
                else if(clas == 3) {
                    db2.execSQL("INSERT INTO INVENTORY_3 VALUES(null, " + idx_potion  + ", '" + item_name + "', " + hp + ", " + mp + ", " + cost + ");");
                    idx_potion++;
                }
            }
        }
        else if(type == "buy") {
            cursor = db.rawQuery("SELECT * FROM ITEM WHERE item_id=(SELECT item_id FROM SHOP WHERE idx=" +param_item_id + ");", null);
            while (cursor.moveToNext()) {
                String item_name = cursor.getString(1);
                int attack = cursor.getInt(2);
                int defence = cursor.getInt(3);
                int hp = cursor.getInt(4);
                int mp = cursor.getInt(5);
                int cost = cursor.getInt(6);
                int clas = cursor.getInt(7);
                if(clas == 1) {
                    db2.execSQL("INSERT INTO INVENTORY_1 VALUES(null, " + idx_weapon + ", " + 0 + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                    idx_weapon++;
                }
                else if(clas == 2) {
                    db2.execSQL("INSERT INTO INVENTORY_2 VALUES(null, " + idx_armor + ", " + 0 + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                    idx_armor++;
                }
                else if(clas == 3) {
                    db2.execSQL("INSERT INTO INVENTORY_3 VALUES(null, " + idx_potion + ", '" + item_name + "', " + hp + ", " + mp + ", " + cost + ");");
                    idx_potion++;
                }
            }
        }
    }

    //clas- 1:무기 2:방어구장착 아이템 확인 함수
    public int equipment_item(int clas){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        if(clas == 1){
            str = "_1";
        }
        else{
            str = "_2";
        }
        Cursor cursor = db.rawQuery("SELECT idx FROM INVENTORY" + str + " WHERE is_equip=1;", null);

        //장착된것이 없음
        if(cursor.moveToFirst() == false)
            return -1;
        else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    //장착 아이템 변경함수 (변경 능력치값 리턴)
    public HashMap<String, Integer> change_equipment_item(int clas, int a_itemslot, int b_itemslot) {
        SQLiteDatabase db = getWritableDatabase();  //장착변수(is_equip) 변경
        SQLiteDatabase db2 = getReadableDatabase();  //전 아이템의 능력치
        SQLiteDatabase db3 = getReadableDatabase();  //사용할 아이템의 능력치

        //리턴할 hashmap (변경 공격력, 방어력 정보가 있음)
        int attack = 0;
        int defence = 0;
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        String str = "";
        String str1;    //db테이블 검색
        Cursor cursor2 = null;
        Cursor cursor3 = null;

        if (clas == 1) {
            str1 = "_1";
        } else {
            str1 = "_2";
        }

        //게임시작후 초기화때 & 장비를 처음 착용할때 조건 (장착 된 것이 없는 상태 -1)
        if(a_itemslot == -1) {
            if(b_itemslot != -1) {
                str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE idx=" + b_itemslot + ";";
                cursor3 = db3.rawQuery(str, null);
                cursor3.moveToFirst();
                attack = cursor3.getInt(0);
                defence = cursor3.getInt(1);
                hashMap.put("attack", attack);
                hashMap.put("defence", defence);
                db.execSQL("UPDATE INVENTORY" + str1 + " SET is_equip=1 WHERE idx=" + b_itemslot + ";");
                return hashMap;
            }
            else if (b_itemslot == -1){
                hashMap.put("attack", attack);
                hashMap.put("defence", defence);
                return hashMap;
            }
        }
        //장비 착용중이며 교체시
        else if(a_itemslot != -1 && b_itemslot != -1){
            str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE idx=" + a_itemslot + ";";
            cursor2 = db2.rawQuery(str, null);
            str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE idx=" + b_itemslot + ";";
            cursor3 = db3.rawQuery(str, null);
            cursor2.moveToFirst();
            cursor3.moveToFirst();
            attack = cursor3.getInt(0) - cursor2.getInt(0);
            defence = cursor3.getInt(1) - cursor2.getInt(1);
            hashMap.put("attack", attack);
            hashMap.put("defence", defence);
            str = "UPDATE INVENTORY" + str1 + " SET is_equip=0 WHERE idx=" + a_itemslot + ";";
            db.execSQL(str);
            str = "UPDATE INVENTORY" + str1 + " SET is_equip=1 WHERE idx=" + b_itemslot + ";";
            db.execSQL(str);
        }
        return hashMap;
    }

    //아이템 판매 처리함수 (판매아이템가격, 장착된 아이템일시 능력치 감소량 리턴)
    public HashMap<String, Integer> sell_item(int idx, int clas){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        String a;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db2 = getWritableDatabase();

        if(clas == 1){
            a = "_1";
        }
        else if(clas == 2){
            a = "_2";
        }
        else{
            a = "_3";
        }
        Cursor cursor = db.rawQuery("SELECT attack, defence, cost, is_equip FROM INVENTORY" + a + " WHERE idx=" + idx + ";", null);
        cursor.moveToFirst();
        if(cursor.getInt(3) == 1) {
            hashMap.put("attack", cursor.getInt(0));
            hashMap.put("defence", cursor.getInt(1));
            hashMap.put("cost", cursor.getInt(2) / 2);
        }
        else{
            hashMap.put("attack", 0);
            hashMap.put("defence", 0);
            hashMap.put("cost", cursor.getInt(2) / 2);
        }
        db2.execSQL("DELETE FROM INVENTORY" + a + " WHERE idx=" + idx + ";");
        if(a.equals("_1")){
            idx_weapon--;
        }
        else if(a.equals("_2")){
            idx_armor--;
        }

        Cursor cursor1 = db.rawQuery("SELECT idx FROM INVENTORY" + a + " WHERE idx>" + idx + ";", null);
        while (cursor1.moveToNext()){
            db2.execSQL("UPDATE INVENTORY" + a + " SET idx="+ (cursor1.getInt(0)-1) +" WHERE idx=" + cursor1.getInt(0) + ";");
        }
        return hashMap;
    }

    //포션 사용시 사용할 함수
    public HashMap<String, Integer> use_potion(int idx){
        HashMap<String, Integer> heal_value = new HashMap<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db2 = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT addHp, addMp FROM INVENTORY_3 WHERE idx=" + idx + ";", null);
        cursor.moveToFirst();
        heal_value.put("hp", cursor.getInt(0));
        heal_value.put("mp", cursor.getInt(1));

        db2.execSQL("DELETE FROM INVENTORY_3 WHERE idx=" + idx + ";");
        idx_potion--;

        Cursor cursor1 = db.rawQuery("SELECT idx FROM INVENTORY_3 WHERE idx>" + idx + ";", null);
        while (cursor1.moveToNext()){
            db2.execSQL("UPDATE INVENTORY_3 SET idx="+ (cursor1.getInt(0)-1) +" WHERE idx=" + cursor1.getInt(0) + ";");
        }
        return heal_value;
    }

    //강화작업
    public HashMap<String, String> enhancement(int idx, int clas){
        HashMap<String, String> result = new HashMap<>();
        String a;
        int now_level;
        double value = start_rand(1000);
        int attack;
        int defence;
        int equip_state;
        int rate;   // 10 -> 1%

        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db2 = getWritableDatabase();

        if(clas == 1){
            a = "_1";
        }
        else{
            a = "_2";
        }

        Cursor cursor = db.rawQuery("SELECT enhance, attack, defence, is_equip FROM INVENTORY" + a + " WHERE idx=" + idx + ";", null);
        cursor.moveToFirst();
        now_level = cursor.getInt(0);
        attack = cursor.getInt(1);
        defence = cursor.getInt(2);
        if(cursor.getInt(3) == 1){
            equip_state = 1;
        }
        else
            equip_state = 0;

        rate = enhance_rate(now_level).get("rate");
        result.put("is_equip", String.valueOf(equip_state));

        if(value <= rate){
            attack += enhance_rate(now_level).get("attack");        //성공시 공격력
            defence += enhance_rate(now_level).get("defence");      //성공시 방어력
            db2.execSQL("UPDATE INVENTORY" + a + " SET enhance=" + (now_level + 1) + ", attack="+ attack +", defence="+ defence + " WHERE idx=" + idx + ";");
            result.put("result", "성공");
            result.put("attack", String.valueOf(enhance_rate(now_level).get("attack")));            //성공시 공격력 증가량
            result.put("defence", String.valueOf(enhance_rate(now_level).get("defence")));          //성공시 방어력 증가량
        }
        else {
            if(clas == 1) {
                db2.execSQL("DELETE FROM INVENTORY_1 WHERE idx=" + idx + ";");
                idx_weapon--;
                Cursor cursor1 = db.rawQuery("SELECT idx FROM INVENTORY_1 WHERE idx>" + idx + ";", null);
                while (cursor1.moveToNext()){
                    db2.execSQL("UPDATE INVENTORY_1 SET idx="+ (cursor1.getInt(0)-1) +" WHERE idx=" + cursor1.getInt(0) + ";");
                }
            }
            else{
                db2.execSQL("DELETE FROM INVENTORY_2 WHERE idx=" + idx + ";");
                idx_armor--;
                Cursor cursor1 = db.rawQuery("SELECT idx FROM INVENTORY_2 WHERE idx>" + idx + ";", null);
                while (cursor1.moveToNext()){
                    db2.execSQL("UPDATE INVENTORY_2 SET idx="+ (cursor1.getInt(0)-1) +" WHERE idx=" + cursor1.getInt(0) + ";");
                }
            }
            result.put("result", "실패");
            result.put("attack", String.valueOf(attack));            //실패시 공격력 감소량
            result.put("defence", String.valueOf(defence));          //실패시 방어력 감소량
        }
        return result;
    }

    //강화 비용, 확률 리턴
    public HashMap<String, Integer> enhance_info(int idx, int clas){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        String a;

        SQLiteDatabase db = getReadableDatabase();

        if(clas == 1){
            a = "_1";
        }
        else{
            a = "_2";
        }
        Cursor cursor = db.rawQuery("SELECT enhance FROM INVENTORY" + a + " WHERE idx=" + idx + ";", null);
        cursor.moveToFirst();
        hashMap.put("rate",enhance_rate(cursor.getInt(0)).get("rate")/10);
        hashMap.put("cost",enhance_rate(cursor.getInt(0)).get("cost"));

        return hashMap;
    }

    //강화단계에 대한 비용, 확률, 능력치 증가량
    public HashMap<String, Integer> enhance_rate(int item_level){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        switch (item_level) {
            case 0:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 1000);
                hashMap.put("rate", 1000);
                return hashMap;
            case 1:
            case 2:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 1500);
                hashMap.put("rate", 900);
                return hashMap;
            case 3:
            case 4:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 2000);
                hashMap.put("rate", 850);
                return hashMap;
            case 5:
            case 6:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 2500);
                hashMap.put("rate", 800);
                return hashMap;
            case 7:
            case 8:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 3000);
                hashMap.put("rate", 750);
                return hashMap;
            case 9:
            case 10:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 3500);
                hashMap.put("rate", 700);
                return hashMap;
            case 11:
            default:
                hashMap.put("attack", 1);
                hashMap.put("defence", 1);
                hashMap.put("cost", 100000);
                hashMap.put("rate", 0);
                return hashMap;
        }
    }

    //random
    public int start_rand(int max) {
        int rand_num;
        rand_num = rand.nextInt(max) + 1;
        return rand_num;
    }
    public int start_rand(int max, int offset) {        //max, min
        int rand_num;
        rand_num = rand.nextInt(max-offset) + offset;
        return rand_num;
    }
}