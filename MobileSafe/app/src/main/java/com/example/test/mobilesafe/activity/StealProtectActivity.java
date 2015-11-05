package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.FunctionInfoAdapter;
import com.example.test.mobilesafe.entity.FunctionInfo;
import com.example.test.mobilesafe.utility.Logger;

import java.util.ArrayList;
import java.util.List;

public class StealProtectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "StealProtectActivity";
    private SharedPreferences.Editor editor;
    private Animation animation;
    private Intent intent;
    private View content;
    private FunctionInfoAdapter adapter;
    private List<FunctionInfo> list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steal_protect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animation = AnimationUtils.loadAnimation(this, R.anim.shake);

        listView = (ListView) findViewById(R.id.lv_csp_function_list);
        list = new ArrayList<>();
        listAdd(R.drawable.reset_password,R.string.reset_password);
        adapter = new FunctionInfoAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        content = findViewById(R.id.ll_csp_content);

        intent = new Intent(this, MainActivity.class);

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

    private void listAdd(int drawable, int str) {
        Drawable icon = ContextCompat.getDrawable(this, drawable);
        String name = getResources().getString(str);
        FunctionInfo functionInfo = new FunctionInfo(icon, name);
        list.add(functionInfo);
    }

    private void whetherPasswordSet() {
        String pwd = getSharedPreferences("config", MODE_PRIVATE).getString("password", "");
        if (pwd.equals("")) {
            Logger.i(TAG, "password is " + pwd);
            setPwdDialog();
        } else {
            Logger.i(TAG, "password is " + pwd);
            inputPwdDialog();
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
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = setPwd.getText().toString().trim();
                String password2 = comfirmPwd.getText().toString().trim();
                if (password1.isEmpty()) {
                    comfirmPwd.startAnimation(animation);
                    Toast.makeText(getApplicationContext(),R.string.empty_pwd,Toast.LENGTH_SHORT).show();
                }else if (!password1.equals(password2)) {
                    comfirmPwd.startAnimation(animation);
                    comfirmPwd.setText("");
                    Toast.makeText(getApplicationContext(), R.string.different_pwd, Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("password", password1).commit();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.pwd_setting_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inputPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.password);

        View view = View.inflate(this, R.layout.dialog_content_edittext, null);
        final EditText insertPwd = (EditText) view.findViewById(R.id.et_dce_pwd);

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
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = insertPwd.getText().toString().trim();
                String pwd = getSharedPreferences("config", MODE_PRIVATE).getString("password", "");
                if (password1.isEmpty()) {
                    insertPwd.startAnimation(animation);
                    Toast.makeText(getApplicationContext(), R.string.incorrect_pwd, Toast.LENGTH_SHORT).show();
                } else if (!password1.equals(pwd)) {
                    insertPwd.startAnimation(animation);
                    insertPwd.setText("");
                    Toast.makeText(getApplicationContext(), R.string.incorrect_pwd, Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    content.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.correct_pwd, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                setPwdDialog();
                break;
            default:
                break;
        }
    }
}
