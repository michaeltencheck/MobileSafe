package com.example.test.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.entity.UpdateInfo;
import com.example.test.mobilesafe.utility.HttpWeb;
import com.example.test.mobilesafe.utility.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private URL url;
    private UpdateInfo updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String website = "http://192.168.1.116:8080/update.xml";
        try {
            url = new URL(website);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        getUpdateInfo();
    }

    private void getUpdateInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = HttpWeb.getInputStream(url);
                    updateInfo = XmlParser.updateInfoParser(is);
                } catch (Exception e) {
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
