package com.example.myapplication.BD;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DBMobile";//название бд
    private static final int SCHEMA = 1; //версия бд

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table medicalSupplies ("
                + "Id integer primary key autoincrement,"
                + "Name text" + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
