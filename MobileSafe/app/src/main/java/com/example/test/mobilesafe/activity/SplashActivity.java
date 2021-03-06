package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.entity.UpdateInfo;
import com.example.test.mobilesafe.utility.AppVersion;
import com.example.test.mobilesafe.utility.Downloader;
import com.example.test.mobilesafe.utility.HttpWeb;
import com.example.test.mobilesafe.utility.Logger;
import com.example.test.mobilesafe.utility.XmlParser;

import java.io.File;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int GETUPDATEINFO_SUCCESS = 1;
    private static final int GETUPDATEINFO_FAIL = 2;
    private static final int INSTALLAPK = 3;
    private UpdateInfo updateInfo;
    private View v;
    private ProgressDialog p;
    private Intent intent;
    private File file;
    private String versionName, website,address, path;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETUPDATEINFO_SUCCESS:
                    versionCheck();
                    break;
                case GETUPDATEINFO_FAIL:
                    finish();
                    Logger.i(TAG,"fail");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private void versionCheck() {
        if (versionName.equals(updateInfo.getVersion()) || "0".equals(updateInfo.getVersion())) {
            try {
                Thread.sleep(5555);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
            startActivity(intent);
            Logger.i(TAG, "version same");
        } else {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            String title = getResources().getString(R.string.update);
            b.setTitle(title + updateInfo.getVersion());
            b.setView(v);
            b.setCancelable(false);
            b.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    upgrade();
                }
            });
            b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    startActivity(intent);
                }
            });
            b.create().show();
            Logger.i(TAG, "version different");
        }
    }

    private void upgrade() {
        p.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    file = new File(path);
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        if (file.exists()) {
                            p.dismiss();
                            installApk(file);
                        } else {
                            p.dismiss();
                            file = Downloader.downloadFile(address, path, p);
                            installApk(file);
                        }
                    } else {
                        path = Environment.getDataDirectory().getAbsolutePath()
                                + address.substring(address.lastIndexOf("/"));
                        file = new File(path);
                        if (file.exists()) {
                            p.dismiss();
                            installApk(file);
                        } else {
                            p.dismiss();
                            file = Downloader.downloadFile(address, path, p);
                            installApk(file);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent,INSTALLAPK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSTALLAPK) {
            if (resultCode == RESULT_CANCELED) {
                finish();
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        v = View.inflate(this, R.layout.dialog_content, null);

        intent = new Intent(this, MainActivity.class);

        p = new ProgressDialog(this);
        p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        updateInfo = new UpdateInfo("0", "0", "0");

        website = "http://192.168.1.116:8080/update.xml";

        try {
            versionName = AppVersion.getVersionName(this);
            Logger.i(TAG, versionName);
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
                    address = updateInfo.getApkUrl();
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            address.substring(address.lastIndexOf("/"));
                    Logger.i(TAG, updateInfo.getApkUrl());
                    Logger.i(TAG, updateInfo.getVersion());
                    Logger.i(TAG, updateInfo.getDescription());
                    handler.sendEmptyMessage(GETUPDATEINFO_SUCCESS);
                } catch (Exception e) {
                    handler.sendEmptyMessage(GETUPDATEINFO_FAIL);
                    Logger.i(TAG,"fail to get info");
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
