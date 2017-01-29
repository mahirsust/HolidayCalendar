package com.example.mahirhasan.registrationandlogin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public  class listviewAdapter extends BaseAdapter {

    public ArrayList<Holiday> list;
    Activity activity;

    public listviewAdapter(Activity activity, ArrayList<Holiday> productList) {
        super();
        this.activity = activity;
        this.list = productList;
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
    public View getView(int index, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.listview_row, parent, false);
        }

        final Holiday data = list.get(index);

        TextView Event =  (TextView) view.findViewById(R.id.event);
        Event.setText(data.getName());

        TextView Category =  (TextView) view.findViewById(R.id.category);
        Category.setText(data.getCategory());

        TextView Date =  (TextView) view.findViewById(R.id.date);
        Date.setText(data.getDate());



        return view;
    }




}