package com.example.hong.listcomplete;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BUYLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, item TEXT, amount INTEGER, live INTEGER, withdraw INTEGER);");
        db.execSQL("CREATE TABLE BACKUPLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, item TEXT, amount INTEGER, live INTEGER, withdraw INTEGER);");


        db.execSQL("CREATE TABLE USERPRODUCTLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, title TEXT, enrollDate TEXT, " +
                "date String, amount INTEGER, position TEXT, category TEXT, detail TEXT, type INTEGER );");
        // BUYLIST : 장바구니 리스트 // BACKUPLIST : 백업리스트 //  USERPRODUCT : 유저 냉장고 내 상품 리스트
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String user_id, String item, int amount, int live) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO BUYLIST VALUES(null, '" + user_id + "', '" + item + "', " + amount + ", " + live + ", 1);");
        db.close();
    }

    public void backUp(String user_id, String item, int amount, int live) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO BACKUPLIST VALUES(null, '" + user_id + "', '" + item + "', " + amount + ", " + live + ", 0);");
        db.close();
    }
// 유저 아이디 , 상품명, 등록일, 유통기한, 수량, 저장장소(상온/냉장/냉동) , 카테고리, 세부사항, 타입
    public void insertuser(String user_id, String title, String enrollDate, String date, int amount, String position, String category, String detail, int type) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USERPRODUCTLIST VALUES(null, '" + user_id + "', '" + title + "', '" + enrollDate + "', '" + date + "', " +
                " " + amount + ",'" + position + "','" + category + "','" + detail + "'," + type + ");");
        db.close();
    }

    public void update(String user_id, String item, int amount, int live, int withdraw) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE BUYLIST SET amount=" + amount + "  WHERE item='" + item + "';");
        db.execSQL("UPDATE BUYLIST SET live=" + live + " WHERE item='" + item + "';");
        db.execSQL("UPDATE BUYLIST SET withdraw=" + withdraw + " WHERE item='" + item + "';");
        db.close();
    }

    public void update(String item, int amount) { // 디테일에서 수량입력
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE BUYLIST SET amount=" + amount + "  WHERE item='" + item + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM BUYLIST WHERE item= '" + item + "';");
        db.close();
    }

    public void delete2(String title) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM USERPRODUCTLIST WHERE title= '" + title + "';");
        db.close();
    }

    public void returnbackup(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM BACKUPLIST WHERE item= '" + item + "';");
        db.close();
    }
    // DB확인용 제거예정
    public String getResult1() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM NEWLIST", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + " "
                    + "\n";
        }
        return result;
    }
    // DB확인용 제거예정
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM USERPRODUCTLIST", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(1)
                    + " "
                    + cursor.getString(2)
                    + " "
                    + cursor.getString(3)
                    + " "
                    + cursor.getString(4)
                    + " "
                    + cursor.getInt(5)
                    + " "
                    + cursor.getString(6)
                    + " "
                    + cursor.getString(7)
                    + " "
                    + cursor.getString(8)
                    + " "
                    + cursor.getInt(9)
                    + " \n"
            ;
        }
        return result;
    }

    public ArrayList<BuyProduct> getBuyProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuyProduct> bps = new ArrayList<>();

        SQLiteDatabase db2 = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUYLIST", null);

        while (cursor.moveToNext()) {
            bps.add(new BuyProduct(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }
        return bps;
    }


    public ArrayList<BuyProduct> getBackUpBuyProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<BuyProduct> bps = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM BACKUPLIST", null);
        while (cursor.moveToNext()) {
            bps.add(new BuyProduct(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
        }
        return bps;
    }

    public ArrayList<UserProduct> getUserProduct() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<UserProduct> ups = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM USERPRODUCTLIST", null);

        while (cursor.moveToNext()) {
            ups.add(new UserProduct(R.drawable.trash, cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getString(4)
                    , cursor.getInt(5)
                    , cursor.getString(6)
                    , cursor.getString(7)
                    , cursor.getString(8)
                    , cursor.getInt(9)
            ));
        }
        return ups;
    }


}

