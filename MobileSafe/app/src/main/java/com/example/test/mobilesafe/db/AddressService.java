package com.example.test.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.test.mobilesafe.utility.Logger;

/**
 * Created by test on 12/23/2015.
 */
public class AddressService {
    public static String getAddress(String number) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address.db";
        Logger.i("AddressService",path);
        String address = "该号码无法查询";
        int len = number.length();
        String pattern = "^1[3458]\\d{9}$";
        if (number.matches(pattern)) {
            SQLiteDatabase database = AddressDao.getDb(path);
            Logger.i("AddressService","1");
            if (database.isOpen()) {
                Cursor cursor = database.rawQuery("select city from info where mobileprefix=?",
                        new String[]{number.substring(0, 7)});
                Logger.i("AddressService","2");
                if (cursor.moveToNext()) {
                    address = cursor.getString(0);
                    Logger.i("AddressService","3");
                }
                cursor.close();
            }
        }else switch (len) {
            case 7:
                address = "本地号码";
                break;
            case 8:
                address = "本地号码";
                break;
            case 10:
                String area = number.substring(0, 3);
                SQLiteDatabase database = AddressDao.getDb(path);
                if (database.isOpen()) {
                    /*limit 1表示只取所有结果中的一条*/
                    Cursor cursor = database.rawQuery("select city from info where area=? limit 1",
                            new String[]{area});
                    if (cursor.moveToNext()) {
                        address = cursor.getString(0);
                        cursor.close();
                        database.close();
                    }
                }
                break;
        }
        Logger.i("AddressService","4");
        return address;
    }
}
