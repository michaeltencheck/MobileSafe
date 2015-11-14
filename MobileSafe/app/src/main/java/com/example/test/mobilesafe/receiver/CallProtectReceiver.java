package com.example.test.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.test.mobilesafe.utility.Logger;

public class CallProtectReceiver extends BroadcastReceiver {
    private static final String TAG = "CallProtectReceiver";

    public CallProtectReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Logger.i(TAG, "do you see me");
    }
}
