package com.example.test.mobilesafe.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.TelQueryInfoAdapter;
import com.example.test.mobilesafe.entity.FunctionInfo;

import java.util.ArrayList;
import java.util.List;

public class TelQueryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private List<FunctionInfo> list;
    private TelQueryInfoAdapter adapter;
    private ListView listView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_query);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.lv_tqa_list);
        list = new ArrayList<>();
        adapter = new TelQueryInfoAdapter(this, list);
        listView.setAdapter(adapter);

        intent = new Intent(this, QueryNumberActivity.class);

        listView.setOnItemClickListener(this);

        listAdd(R.drawable.ic_my_location_24dp, R.string.tel_location);

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

    private void listAdd(int drawable, String str) {
        Drawable icon = ContextCompat.getDrawable(this, drawable);
        FunctionInfo functionInfo = new FunctionInfo(icon, str);
        list.add(functionInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
