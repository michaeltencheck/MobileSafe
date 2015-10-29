package com.example.test.mobilesafe.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by test on 10/28/2015.
 */
public class AppVersion{
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
        return info.versionName;
    }
}
