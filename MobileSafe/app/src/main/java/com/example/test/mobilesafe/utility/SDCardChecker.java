package com.example.test.mobilesafe.utility;

import android.os.Environment;

import java.io.File;

/**
 * Created by test on 10/29/2015.
 */
public class SDCardChecker {
    public static final int FILE_EXISTS = 0;
    public static final int FILE_NOT_EXISTS = 1;
    public static final int INTERNAL_EXISTS = 2;
    public static final int INTERNAL_NOT_EXISTS = 3;

    public static int sdCardCheck(File file) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            if (file.exists()) {
                return FILE_EXISTS;
            } else {
                return FILE_NOT_EXISTS;
            }
        } else {
            if (file.exists()) {
                return INTERNAL_EXISTS;
            } else {
                return INTERNAL_NOT_EXISTS;
            }
        }
    }
}
