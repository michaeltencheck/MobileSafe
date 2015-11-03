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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.utility.Logger;

public class StealProtectActivity extends AppCompatActivity {
    private static final String TAG = "StealProtectActivity";
    private SharedPreferences.Editor editor;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steal_protect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animation = AnimationUtils.loadAnimation(this, R.anim.shake);

        editor = getSharedPreferences("config", MODE_PRIVATE).edit();

        whetherPasswordSet();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void whetherPasswordSet() {
        String pwd = getSharedPreferences("config", MODE_PRIVATE).getString("password", "");
        if (pwd.equals("")) {
            Logger.i(TAG, "password is " + pwd);
            setPwdDialog();
        } else {
            Logger.i(TAG, "password is " + pwd);
            getAlertDialog();
        }
    }

    private void setPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.set_password);

        View view = View.inflate(this, R.layout.password_setting, null);
        final EditText setPwd = (EditText) view.findViewById(R.id.set_password);
        final EditText comfirmPwd = (EditText) view.findViewById(R.id.reset_password);

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
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = setPwd.getText().toString().trim();
                String password2 = comfirmPwd.getText().toString().trim();
                /*if (password1.equals(password2) & !(password1.isEmpty())) {
                    editor.putString("password", password1).commit();
                    dialog.dismiss();
                } else {
                    comfirmPwd.startAnimation(animation);
                }*/
                if (password1.isEmpty()) {
                    comfirmPwd.startAnimation(animation);
                    Toast.makeText(getApplicationContext(),R.string.empty_pwd,Toast.LENGTH_SHORT).show();
                }else if (!password1.equals(password2)) {
                    comfirmPwd.startAnimation(animation);
                    Toast.makeText(getApplicationContext(), R.string.different_pwd, Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("password", password1).commit();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.pwd_setting_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
