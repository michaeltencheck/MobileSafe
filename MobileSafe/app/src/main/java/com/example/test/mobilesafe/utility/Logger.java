package com.example.test.mobilesafe.utility;

import android.util.Log;

/**
 * Created by test on 10/28/2015.
 */
public class Logger {
    private static int VERBOSE = 1;
    private static int INFO = 2;
    private static int DEBUG = 3;
    private static int WARN = 4;
    private static int ERROR = 5;
    private static int LOGLEVEL = 6;

    public static void v(String tag, String msg) {
        if (LOGLEVEL > VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOGLEVEL > INFO) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOGLEVEL > DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGLEVEL > WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGLEVEL > ERROR) {
            Log.e(tag, msg);
        }
    }
}
