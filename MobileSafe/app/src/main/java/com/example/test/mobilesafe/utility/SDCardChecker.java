package com.example.test.mobilesafe.utility;

import android.os.Environment;

import java.io.File;

/**
 * Created by test on 10/29/2015.
 */
public class SDCardChecker {
    private static final int FILE_EXISTS = 0;
    private static final int FILE_NOT_EXISTS = 1;
    private static final int SDCARD_UNAVAILABLE = 2;

    public static int sdCardCheck(File file) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (file.exists()) {
                return FILE_EXISTS;
            } else {
                return FILE_NOT_EXISTS;
            }
        } else {
            return SDCARD_UNAVAILABLE;
        }
    }
}
