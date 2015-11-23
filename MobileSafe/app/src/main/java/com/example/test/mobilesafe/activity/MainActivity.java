package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.FunctionInfoAdapter;
import com.example.test.mobilesafe.entity.FunctionInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FunctionInfoAdapter adapter;
    private List<FunctionInfo> list;
    private ListView listView;
    private SharedPreferences sp;
    private String line0, line1, line2, line3, line4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_am_function_list);


        final Intent intent0 = new Intent(this, StealProtectActivity.class);
        final Intent intent1 = new Intent(this, InfoProtectActivity.class);
        final Intent intent2 = new Intent(this, VirusKillerActivity.class);


        list = new ArrayList<>();
        adapter = new FunctionInfoAdapter(this, list);

        line0 = getResources().getString(R.string.steal_protect);
        line1 = getResources().getString(R.string.info_protect);
        line2 = getResources().getString(R.string.kill_virus);
        line3 = getResources().getString(R.string.app_manager);
        line4 = getResources().getString(R.string.tel_query);

        String reset = getResources().getString(R.string.reset_title);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        String title0 = sp.getString("title0", line0);
        String title1 = sp.getString("title1", line1);
        String title2 = sp.getString("title2", line2);
        String title3 = sp.getString("title3", line3);
        String title4 = sp.getString("title", line4);

        listAdd(R.drawable.ic_protect, title0);
        listAdd(R.drawable.ic_accessibility, title1);
        listAdd(R.drawable.ic_kill_virus_24dp, title2);
        listAdd(R.drawable.app_manager, title3);
        listAdd(R.drawable.tel_query, title4);

        listAdd(R.drawable.reset_password, reset);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(intent0);
                        break;
                    case 1:
                        startActivity(intent1);
                        break;
                    case 2:
                        startActivity(intent2);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        sp.edit().putString("title0", line0).commit();
                        sp.edit().putString("title1", line1).commit();
                        list.get(0).setName(line0);
                        list.get(1).setName(line1);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != (list.size() - 1)) {
                    changeTitle(position);
                    return true;
                } else {
                    return false;
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeTitle(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_title);

        View view = View.inflate(this, R.layout.change_title, null);
        final EditText title = (EditText) view.findViewById(R.id.et_ct_change_title);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.comfirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = title.getText().toString().trim();
                FunctionInfo info = list.get(position);
                info.setName(str);
                adapter.notifyDataSetChanged();
                sp.edit().putString("title" + position, str).commit();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
