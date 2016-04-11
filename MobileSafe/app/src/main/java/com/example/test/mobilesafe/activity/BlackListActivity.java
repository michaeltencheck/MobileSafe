package com.example.test.mobilesafe.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.BlackListAdapter;
import com.example.test.mobilesafe.entity.BlackList;
import com.example.test.mobilesafe.entity.FunctionInfo;
import com.example.test.mobilesafe.utility.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BlackListActivity extends AppCompatActivity {
    private static final int PICK_CONTACT = 1;
    private static final String TAG = "BlackListActivity";
    private EditText editText;
    private String number;
    private ListView listView;
    private BlackListAdapter adapter;
    private List<BlackList> lists;
    private Button add;
    private Button choose;
    private Uri uriContact;
    private String contactID;

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
        if (lists.isEmpty()) {
            lists.add(blackList);
        }
        for (int i = 0; i < lists.size(); i++) {
            String existNumber = lists.get(i).getTel_number();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    /*    switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = getContentResolver().query(contactData, null, null, null, null);

                    if (cursor.moveToNext()) {
                        String name = cursor.getString
                                (cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String number = cursor.getString
                                (cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        *//*调试后出现cursor unavailable，今天心有挂牵，to be continues*//*
                        *//*今天研究海淘，先修整修整*//*
                        listAdd(name,number);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }*/
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactNumber();

        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Logger.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString
                    (cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            listAdd("unknown", contactNumber);
            adapter.notifyDataSetChanged();
        }

        cursorPhone.close();

        Logger.d(TAG, "Contact Phone Number: " + contactNumber);
    }


   /* private void retrieveContactPhoto() {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
                ImageView imageView = (ImageView) findViewById(R.id.img_contact);
                imageView.setImageBitmap(photo);
            }

            assert inputStream != null;
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
