package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.entity.FunctionInfo;

import java.util.List;

/**
 * Created by test on 11/1/2015.
 */
public class QueryNumberAdapter extends BaseAdapter{
    private Context context;
    private List<FunctionInfo> list;

    public QueryNumberAdapter(Context context, List<FunctionInfo> list) {
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
        ViewHolder viewholder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_function, null);
            viewholder = new ViewHolder();
            viewholder.imageView = (ImageView) view.findViewById(R.id.iv_if_icon);
            viewholder.textView = (TextView) view.findViewById(R.id.tv_if_name);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.imageView.setImageDrawable(list.get(position).getDrawable());
        viewholder.textView.setText(list.get(position).getName());
        return view;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
