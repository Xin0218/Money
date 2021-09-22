package com.example.recordaccounts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private final static String _TableName = "TestSQL";
    private final static String _TableName2 = "RyanSQL";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " +
                _TableName +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Type VARCHAR(50)," +
                "Value VARCHAR(50))";
        db.execSQL(SQLTable);

        String SQLTable2 = "CREATE TABLE IF NOT EXISTS " +
                _TableName2 +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Type VARCHAR(50)," +
                "Value VARCHAR(50))";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        final String SQL2 = "DROP TABLE " + _TableName2;

        db.execSQL(SQL);
        db.execSQL(SQL2);
    }

    //檢查資料表狀態
    public void checkTable() {
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                        _TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0) {
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + _TableName +
                        "( " + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "Type TEXT, " +
                        "Value TEXT, " +
                        "Hobby TEXT, " + ");");
                cursor.close();
            }
        }
    }

    //取得有多少資料表
    public ArrayList<String> getTables() {
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master", null);
        ArrayList<String> tables = new ArrayList<>();
        while (cursor.moveToNext()) {
            String getTab = new String(cursor.getBlob(0));
            if (getTab.contains("android_metadata")) {
            } else if (getTab.contains("sqlite_sequence")) {
            } else tables.add(getTab.substring(0, getTab.length() - 1));
        }
        return tables;

    }

    //新增資料
    public void addData(String key, String value) {
        Log.v("Ryan", "key: " + key + " /value: " + value);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", key);
        values.put("value", value);
        db.insert(_TableName, null, values);
    }

    //新增資料
    public void addData2(String key, String value, String value2) {
        Log.v("Ryan", "key: " + key + " /value: " + value);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", key);
        values.put("value", value);
        values.put("describe", value2);
        db.insert(_TableName2, null, values);
    }

    //顯示所有資料
    public ArrayList<HashMap<String, String>> showAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + _TableName2, null);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();
            String key = c.getString(1);
            String price = c.getString(2);
            String input = c.getString(3);
            hashMap.put("type", key);
            hashMap.put("value", price);
            hashMap.put("describe", input);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    //刪除table
    public void delete() {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + _TableName;
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //查詢
    public void search(String type) {
        String where = "type = " + type;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TestSQL WHERE type =?", new String[]{"home"});
        Log.v("Ryan", "Ryan_ CURSOR: " + cursor.moveToFirst());
    }
}
