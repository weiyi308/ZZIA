package com.rdc.ruan.zzia.Main.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ruan on 2015/7/5.
 */
public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public void add(String id,String passwprd){
        db.beginTransaction();
        db.execSQL("insert into user(_id,password) values(?,?)",new Object[]{id,passwprd});
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public String[] selectId(){
        Cursor cursor = db.rawQuery("select _id from user",null);
        String[] result = new String[cursor.getCount()];
        int i =0;
        while (cursor.moveToNext()){
            result[i] = cursor.getString(cursor.getColumnIndex("_id"));
            i++;
        }
        cursor.close();
        return result;
    }
    public int getCount(String table){
        Cursor cursor = db.rawQuery("select * from "+table,null);
        return cursor.getCount();
    }
    public boolean contains(String id){
        Cursor cursor = db.rawQuery("select _id from user where _id=?",new String[]{id});
        return (0 != cursor.getCount());
    }
    public String selectPassword(String id){
        Cursor cursor = db.rawQuery("select * from user where _id=?",new String[]{id});
        if (cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("password"));
        }
        return "";
    }
    public void delete(String id){
        db.delete("user","_id == ?",new String[]{id});
    }
    public void update(String id,String password){
        ContentValues cv = new ContentValues();
        cv.put("password",password);
        db.update("user",cv,"_id == ?",new String[]{id});
    }
    public void closeDB(){
        db.close();
    }
}
