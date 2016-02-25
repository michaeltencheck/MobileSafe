package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_black_list, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.tv_ibl_name);
            viewHolder.tel_number = (TextView) view.findViewById(R.id.tv_ibl_number);
            view.setTag(viewHolder);
        } else {

        }
        return null;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView tel_number;
    }
}
