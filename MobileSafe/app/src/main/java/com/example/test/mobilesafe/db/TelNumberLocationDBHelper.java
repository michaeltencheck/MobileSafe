package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by test on 12/19/2015.
 */
public class TelNumberLocationDBHelper extends SQLiteOpenHelper{
    private Context context;

    public TelNumberLocationDBHelper(Context context) {
        super(context, "address.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
