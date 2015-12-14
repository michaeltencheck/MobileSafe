package com.example.test.mobilesafe.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.QueryNumberAdapter;
import com.example.test.mobilesafe.entity.FunctionInfo;
import com.example.test.mobilesafe.utility.Downloader;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class QueryNumberActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private List<FunctionInfo> list;
    private QueryNumberAdapter adapter;
    private File file;
    private String path;
    private String state;
    private String website;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_number);
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

        listView = (ListView) findViewById(R.id.lv_qna_listview);
        list = new ArrayList<>();
        listAdd(R.drawable.ic_aspect_ratio_24dp,R.string.show_place);
        adapter = new QueryNumberAdapter(this, list);
        listView.setAdapter(adapter);

        state = Environment.getExternalStorageState();
        progressDialog = new ProgressDialog(this);

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/antivirus.db";
        } else {
            path = this.getFilesDir() + "/antivirus.db";
        }

        file = new File(path);
        if (!file.exists()) {
            try {
                progressDialog.show();
                Downloader.downloadFile(website, path, progressDialog);
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            default:
                break;
        }
    }

    private void listAdd(int drawable, int str) {
        Drawable icon = ContextCompat.getDrawable(this, drawable);
        String name = getResources().getString(str);
        FunctionInfo functionInfo = new FunctionInfo(icon, name);
        list.add(functionInfo);
    }
}
