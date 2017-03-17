package com.cappamessenger.two;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Dkflbc on 15.03.17.
 */

public class MessageAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater layoutInflater;
    ArrayList<MessagesGroup> objects;

    MessageAdapter(Context context,ArrayList<MessagesGroup> message){

        ctx = context;
        objects = message;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.messages,parent,false);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }



}
