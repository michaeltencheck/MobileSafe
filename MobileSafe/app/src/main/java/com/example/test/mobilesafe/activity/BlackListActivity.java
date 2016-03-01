package com.example.test.mobilesafe.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.BlackListAdapter;
import com.example.test.mobilesafe.entity.BlackList;

import java.util.ArrayList;
import java.util.List;

public class BlackListActivity extends AppCompatActivity {
    private EditText editText;
    private String number;
    private ListView listView;
    private BlackListAdapter adapter;
    private List<BlackList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
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

        editText = (EditText) findViewById(R.id.et_cbl_input_number);
        number = editText.getText().toString().trim();
        listView = (ListView) findViewById(R.id.lv_cbl_list_view);
        lists = new ArrayList<>();
        adapter = new BlackListAdapter(this, lists);
    }

}
