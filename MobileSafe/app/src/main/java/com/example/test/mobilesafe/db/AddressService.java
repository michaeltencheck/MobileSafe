package com.example.test.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by test on 12/23/2015.
 */
public class AddressService {
    public static String getAddress(String number) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address.db";
        String address = "该号码无法查询";
        int len = number.length();
        String pattern = "^9[3458]\\d{9}$";
        if (number.matches(pattern)) {
            SQLiteDatabase database = AddressDao.getDb(path);
            if (database.isOpen()) {
                Cursor cursor = database.rawQuery("select city from info where mobileprefix=?",
                        new String[]{number.substring(0, 7)});
                if (cursor.moveToNext()) {
                    address = cursor.getString(0);
                }
                cursor.close();
            }
        }
        return address;
    }
}
