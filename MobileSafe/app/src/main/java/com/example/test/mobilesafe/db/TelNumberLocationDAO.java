package com.example.test.mobilesafe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by test on 12/20/2015.
 */
public class TelNumberLocationDAO {
    private Context context;
    private TelNumberLocationDBHelper helper;

    public TelNumberLocationDAO(Context context) {
        this.context = context;
        helper = new TelNumberLocationDBHelper(context);
    }

    public String findLocation(String number) {
        String address = null;
        SQLiteDatabase database = helper.getReadableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("", new String[]{number});
            if (cursor.moveToNext()) {
                address = cursor.getString(cursor.getColumnIndex(""));
            }
            cursor.close();
        }
        return address;
    }
}
