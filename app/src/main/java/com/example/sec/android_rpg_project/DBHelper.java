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

    public DBHelper(Context context){
        super(context, "MyDB.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격, 아이템클래스(0, 1, 2, 3)
        //item_id = 1부터 시작
        sqLiteDatabase.execSQL("CREATE TABLE ITEM (item_id INTEGER PRIMARY KEY AUTOINCREMENT, item_name TEXT, attack INTEGER, defence INTEGER, addHp INTEGER, addMp INTEGER, cost INTEGER, class INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '골드', 0, 0, 0, 0, 0, 0);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '목검', 5, 0, 0, 0, 500, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '청동검', 7, 0, 0, 0, 5000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '강철검', 9, 0, 0, 0, 50000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '다이아몬드검', 11, 0, 0, 0, 500000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '헌 가죽 옷', 0, 5, 0, 0, 500, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '가죽 옷', 0, 7, 0, 0, 5000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '청동 갑옷', 0, 9, 0, 0, 50000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '가벼운 강철 갑옷', 0, 11, 0, 0, 500000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, '무적갑옷', 0, 9999, 0, 0, 5000000, 2);");
        sqLiteDatabase.execSQL("INSERT INTO ITEM VALUES(null, 'HP 포션', 0, 0, 30, 0, 500, 3);");

        sqLiteDatabase.execSQL("CREATE TABLE MOB (mob_id INTEGER PRIMARY KEY AUTOINCREMENT, mob_name TEXT, hp INTEGER, damage INTEGER, exp INTEGER, is_boss INTEGER);");  //
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨1 몬스터', 20, 3, 3, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨2 몬스터', 30, 5, 7, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨3 몬스터', 40, 8, 11, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨4 몬스터', 45, 11, 17, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨5 몬스터', 50, 16, 25, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨6 몬스터', 70, 23, 37, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨7 몬스터', 94, 31, 52, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨8 몬스터', 111, 42, 73, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨9 몬스터', 143, 53, 94, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '레벨10 몬스터', 165, 66, 115, 0);");
        sqLiteDatabase.execSQL("INSERT INTO MOB VALUES(null, '제 1 보스', 300, 50, 600, 1);");

        //index, 아이템번호, 몬스터번호, 최소개수, 최대개수, 드롭률
        sqLiteDatabase.execSQL("CREATE TABLE DROP_ITEM (idx INTEGER PRIMARY KEY AUTOINCREMENT, item_id INTEGER, mob_id INTEGER, min INTEGER, max INTEGER, ratio INTEGER);");  //ratio -> 10 = 1%
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 0, 10, 20, 800);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 1, 20, 24, 800);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 2, 30, 40, 800);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 3, 40, 50, 800);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 1, 4, 60, 80, 800);");  //골드
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 0, 0, 1, 900);");  //목검
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 1, 0, 1, 900);");  //목검
        sqLiteDatabase.execSQL("INSERT INTO DROP_ITEM VALUES(null, 2, 2, 0, 1, 900);");  //목검

        // 샵위치아이템번호, 아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 종류
        sqLiteDatabase.execSQL("CREATE TABLE SHOP (idx INTEGER PRIMARY KEY AUTOINCREMENT, item_id INTEGER, item_name TEXT, attack INTEGER, defence INTEGER, addHp INTEGER, addMp INTEGER, cost INTEGER, class INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 2, '청동검', 7, 0, 0, 0, 5000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 2, '청동검', 7, 0, 0, 0, 5000, 1);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 11, 'HP 포션', 0, 0, 30, 0, 500, 3);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 11, 'HP 포션', 0, 0, 30, 0, 500, 3);");
        sqLiteDatabase.execSQL("INSERT INTO SHOP VALUES(null, 11, 'HP 포션', 0, 0, 30, 0, 500, 3);");

        // 아이템이위치한칸, 아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_1 (slot INTEGER PRIMARY KEY, item_id INTEGER, item_name TEXT, attack INTEGER, defence INTEGER, cost INTEGER, is_equip INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_2 (slot INTEGER PRIMARY KEY, item_id INTEGER, item_name TEXT, attack INTEGER, defence INTEGER, cost INTEGER, is_equip INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격, 착용여부(0 or 1)
        sqLiteDatabase.execSQL("CREATE TABLE INVENTORY_3 (slot INTEGER PRIMARY KEY, item_id INTEGER, item_name TEXT, addHp INTEGER, addMp INTEGER, cost INTEGER);");  //아이템이위치한칸, 아이템번호, 이름, 레벨제헌, 공, 방어, 피회복, 마나회복, 가격

        //test value
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_2 VALUES(null, 8, '무적갑옷', 0, 9999, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_2 VALUES(null, 4, '헌 가죽 옷', 0, 10, 5000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 2, '목검', 5, 0, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 2, '목검', 5, 0, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 2, '목검', 5, 0, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 2, '목검', 5, 0, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_1 VALUES(null, 2, '목검', 5, 0, 5000000, 0);");
        sqLiteDatabase.execSQL("INSERT INTO INVENTORY_3 VALUES(null, 9, 'HP 포션', 30, 0, 500);");
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

        // 샵위치아이템번호, 아이템번호, 이름, 공, 방어, 피회복, 마나회복, 가격, 종류
        Cursor cursor = db.rawQuery("SELECT * FROM SHOP;", null);
        while (cursor.moveToNext()) {
            if(cursor.getInt(8) == 1 || cursor.getInt(8) == 2)
                result += "이름 : " + cursor.getString(2)
                        + "\n공격력 : " + cursor.getInt(3)
                        + "\t\t\t방어력 : " + cursor.getInt(4)
                        + "\t\t\t가격 : " + cursor.getInt(7);
            else
                result += "이름 : " + cursor.getString(2)
                        + "\t\t\t\tHP회복량 : " + cursor.getInt(5)
                        + "\t\t\t\tMP회복량 : " + cursor.getInt(6)
                        + "\n가격 : " + cursor.getInt(7);
            result_array.add(result);
            result = "";
        }
        return result_array;
    }

    public int getcost(int idx){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cost FROM SHOP WHERE idx=" +idx + ";", null);

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
        while (cursor.moveToNext()) {
            if(clas == 1 || clas == 2)
                result += "이름 : " + cursor.getString(2)
                        + "\n공격력 : " + cursor.getInt(3)
                        + "\t\t\t방어력 : " + cursor.getInt(4)
                        + "\t\t\t가격 : " + (int)(cursor.getInt(5)/2);
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
        int drop = 0;
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
                int item_id = cursor.getInt(0);
                String item_name = cursor.getString(1);
                int attack = cursor.getInt(2);
                int defence = cursor.getInt(3);
                int hp = cursor.getInt(4);
                int mp = cursor.getInt(5);
                int cost = cursor.getInt(6);
                int clas = cursor.getInt(7);
                if(clas == 1)
                    db2.execSQL("INSERT INTO INVENTORY_1 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                else if(clas == 2)
                    db2.execSQL("INSERT INTO INVENTORY_2 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                else if(clas == 3)
                    db2.execSQL("INSERT INTO INVENTORY_3 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + hp + ", " + mp + ", " + cost + ");");
            }
        }
        else if(type == "buy") {
            cursor = db.rawQuery("SELECT * FROM SHOP WHERE idx=" + param_item_id, null);
            while (cursor.moveToNext()) {
                int item_id = cursor.getInt(1);
                String item_name = cursor.getString(2);
                int attack = cursor.getInt(3);
                int defence = cursor.getInt(4);
                int hp = cursor.getInt(5);
                int mp = cursor.getInt(6);
                int cost = cursor.getInt(7);
                int clas = cursor.getInt(8);
                if(clas == 1)
                    db2.execSQL("INSERT INTO INVENTORY_1 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                else if(clas == 2)
                    db2.execSQL("INSERT INTO INVENTORY_2 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + attack + ", " + defence + ", " + cost + ", 0);");
                else if(clas == 3)
                    db2.execSQL("INSERT INTO INVENTORY_3 VALUES(null, "+ (item_id) + ", '" + item_name + "', " + hp + ", " + mp + ", " + cost + ");");
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
        Cursor cursor = db.rawQuery("SELECT slot FROM INVENTORY" + str + " WHERE is_equip=1;", null);

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
                str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE slot=" + b_itemslot + ";";
                cursor3 = db3.rawQuery(str, null);
                cursor3.moveToFirst();
                attack = cursor3.getInt(0);
                defence = cursor3.getInt(1);
                hashMap.put("attack", attack);
                hashMap.put("defence", defence);
                str = "UPDATE INVENTORY" + str1 + " SET is_equip=1 WHERE slot=" + b_itemslot + ";";
                db.execSQL(str);
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
            str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE slot=" + a_itemslot + ";";
            cursor2 = db2.rawQuery(str, null);
            str = "SELECT attack, defence FROM INVENTORY" + str1 + " WHERE slot=" + b_itemslot + ";";
            cursor3 = db3.rawQuery(str, null);
            cursor2.moveToFirst();
            cursor3.moveToFirst();
            attack = cursor3.getInt(0) - cursor2.getInt(0);
            defence = cursor3.getInt(1) - cursor2.getInt(1);
            hashMap.put("attack", attack);
            hashMap.put("defence", defence);
            str = "UPDATE INVENTORY" + str1 + " SET is_equip=0 WHERE slot=" + a_itemslot + ";";
            db.execSQL(str);
            str = "UPDATE INVENTORY" + str1 + " SET is_equip=1 WHERE slot=" + b_itemslot + ";";
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
        Cursor cursor = db.rawQuery("SELECT attack, defence, cost, is_equip FROM INVENTORY" + a + " WHERE slot=" + idx + ";", null);
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
        db2.execSQL("DELETE FROM INVENTORY" + a + " WHERE slot=" + idx + ";");

        return hashMap;
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