package com.example.test.mobilesafe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.utility.Logger;

public class DisplayAdjustActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final String TAG = "DisplayAdjustActivity";
    private RelativeLayout relativeLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int dx;
    private int dy;
    private int l;
    private int r;
    private int t;
    private int b;
    private int lastX, lastY;

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

        lastX = sharedPreferences.getInt("last_x", 0);
        lastY = sharedPreferences.getInt("last_y", 0);

        Logger.i(TAG, "x = " + lastX);
        Logger.i(TAG, "y = " + lastY);

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.leftMargin = lastX;
        layoutParams.topMargin = lastY;
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.rl_display_adjust:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();
                        dx = endX - startX;
                        dy = endY - startY;
                        l = relativeLayout.getLeft();
                        r = relativeLayout.getRight();
                        t = relativeLayout.getTop();
                        b = relativeLayout.getBottom();

/*                        if (t < 300) {
                            introduction1.setVisibility(View.INVISIBLE);
                            introduction2.setVisibility(View.VISIBLE);
                        }else if (t > 900) {
                            introduction1.setVisibility(View.VISIBLE);
                            introduction2.setVisibility(View.INVISIBLE);
                        }*/

                        relativeLayout.layout(l + dx, t + dy, r + dx, b + dy);
                        startX = endX;
                        startY = endY;

                        break;
                    case MotionEvent.ACTION_UP:
                        int reX = (int) event.getX();
                        int reY = (int) event.getY();
                        editor.putInt("last_x", startX);
                        editor.putInt("last_y", startY);
                        editor.commit();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }
}
