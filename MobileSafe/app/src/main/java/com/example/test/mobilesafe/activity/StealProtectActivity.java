package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.utility.Logger;

public class StealProtectActivity extends AppCompatActivity {
    private static final String TAG = "StealProtectActivity";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steal_protect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSharedPreferences("config", MODE_PRIVATE).getString("password", "").isEmpty()) {
            editor = getSharedPreferences("config", MODE_PRIVATE).edit();
            editor.putString("password", "");
            editor.commit();
            Logger.i(TAG,"fasdfas");
        }

        editor = getSharedPreferences("config", MODE_PRIVATE).edit();
        editor.putString("password", "nothing");
        editor.commit();

        if (isPasswordSet()) {
            getAlertDialog();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private boolean isPasswordSet() {
        String pwd = getSharedPreferences("config", MODE_PRIVATE).getString("password", "");
        if (pwd.equals("nothing")) {
            return false;
        } else {
            Logger.i(TAG, pwd);
            return true;
        }
    }

    private void getAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.password);
        View view = View.inflate(this, R.layout.dialog_content_edittext, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.comfirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
