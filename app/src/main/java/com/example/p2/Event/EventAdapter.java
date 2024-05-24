package com.example.p2.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p2.R;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mEvents;

    public EventAdapter(Context context, ArrayList<String> events) {
        mContext = context;
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        TextView eventTextView = convertView.findViewById(R.id.eventTextView);
        eventTextView.setText(mEvents.get(position));

        return convertView;
    }
}