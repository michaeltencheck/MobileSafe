package com.example.test.mobilesafe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.utility.Logger;

public class DisplayAdjustActivity extends AppCompatActivity {
    private static final String TAG = "DisplayAdjustActivity";
    private RelativeLayout relativeLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_adjust);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_display_adjust);
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int lastX = sharedPreferences.getInt("last_x", 0);
        int lastY = sharedPreferences.getInt("last_y", 0);

        Logger.i(TAG, "x = " + lastX);
        Logger.i(TAG, "y = " + lastY);

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.leftMargin = lastX;
        layoutParams.topMargin = lastY;
        relativeLayout.setLayoutParams(layoutParams);
    }

}
