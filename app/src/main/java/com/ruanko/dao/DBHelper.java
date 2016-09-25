package com.ruanko.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzuitachi on 2016/5/28.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME="contact.db";
    private static final int VERSION=1;

    public DBHelper(Context context){
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql="create table contact("+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "number,"+"name,"+"phone,"+"email,"+"address,"
                +"gender,"+"relationship,"+"remark)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
