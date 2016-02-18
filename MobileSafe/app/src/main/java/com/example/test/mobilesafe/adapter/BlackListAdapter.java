package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.test.mobilesafe.entity.BlackList;

import java.util.List;

/**
 * Created by test on 2/15/2016.
 */
public class BlackListAdapter extends BaseAdapter{
    private Context context;
    private List<BlackList> list;

    public BlackListAdapter(Context context, List<BlackList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
