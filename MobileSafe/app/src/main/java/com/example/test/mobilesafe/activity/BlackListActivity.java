package com.example.test.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.BlackListAdapter;
import com.example.test.mobilesafe.entity.BlackList;
import com.example.test.mobilesafe.entity.FunctionInfo;
import com.example.test.mobilesafe.utility.Logger;

import java.util.ArrayList;
import java.util.List;

public class BlackListActivity extends AppCompatActivity {
    private static final int PICK_CONTACT = 1;
    private EditText editText;
    private String number;
    private ListView listView;
    private BlackListAdapter adapter;
    private List<BlackList> lists;
    private Button add;
    private Button choose;

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
        listView = (ListView) findViewById(R.id.lv_cbl_list_view);
        lists = new ArrayList<>();
        adapter = new BlackListAdapter(this, lists);
        listView.setAdapter(adapter);

        add = (Button) findViewById(R.id.bt_cbl_add);
        choose = (Button) findViewById(R.id.bt_cbl_choose);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = editText.getText().toString().trim();
                if (number != "" & number != null) {
                    listAdd("unknown", number);
                    Logger.i("BlackListActivity", number);
                    /*正在考虑怎么存储lists，使得每添加一个blacklist都会一直存在*/
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void listAdd(String name, String tel_number) {
        BlackList blackList = new BlackList(name, tel_number);
        lists.add(blackList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                    if (cursor.moveToNext()) {
                        String name = cursor.getString
                                (cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String number = cursor.getString
                                (cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        /*今天研究海淘，先修整修整*/
                        listAdd(name,number);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
