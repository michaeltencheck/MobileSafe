package com.example.test.mobilesafe.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.entity.UpdateInfo;
import com.example.test.mobilesafe.utility.AppVersion;
import com.example.test.mobilesafe.utility.HttpWeb;
import com.example.test.mobilesafe.utility.Logger;
import com.example.test.mobilesafe.utility.XmlParser;

import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int GETUPDATEINFO_SUCCESS = 1;
    private static final int GETUPDATEINFO_FAIL = 2;
    private UpdateInfo updateInfo;
    private TextView tv;
    private View v;
    private String versionName, website;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETUPDATEINFO_SUCCESS:
                    versionCheck();
                    break;
                case GETUPDATEINFO_FAIL:
                    break;
                default:
                    break;
            }
        }
    };

    private void versionCheck() {
        if (versionName.equals(updateInfo.getVersion()) || "0".equals(updateInfo.getVersion())) {
            Dialog dialog = new Dialog(this);
            String title = getResources().getString(R.string.update);
            dialog.setTitle(title + updateInfo.getVersion());
            dialog.setContentView(v);
            dialog.show();
            Logger.i(TAG, "version same");
        } else {
            Dialog dialog = new Dialog(this);
            dialog.setTitle("" + updateInfo.getVersion());
            dialog.setContentView(v);
            dialog.show();
            Logger.i(TAG, "version different");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        v = View.inflate(this, R.layout.dialog_content, null);
        tv = (TextView) v.findViewById(R.id.tv_dc_content);

        updateInfo = new UpdateInfo("0", "0", "0");

        website = "http://192.168.1.116:8080/update.xml";

        AppVersion appVersion = new AppVersion(this);

        try {
            versionName = appVersion.getVersionName();
            Logger.i(TAG,versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getUpdateInfo();
    }

    private void getUpdateInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = HttpWeb.getInputStream(website);
                    updateInfo = XmlParser.updateInfoParser(is);
                    Logger.i(TAG, updateInfo.getApkUrl());
                    Logger.i(TAG, updateInfo.getVersion());
                    Logger.i(TAG, updateInfo.getDescription());
                    handler.sendEmptyMessage(GETUPDATEINFO_SUCCESS);
                } catch (Exception e) {
                    handler.sendEmptyMessage(GETUPDATEINFO_FAIL);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
