package com.example.official;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WorkshopsListAdapter extends ArrayAdapter<WorkshopListItem> {

    private int resourceLayout;
    private Context mContext;

    public WorkshopsListAdapter(Context context, int resource, List<WorkshopListItem> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        WorkshopListItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.orderID);
            TextView tt3 = (TextView) v.findViewById(R.id.status);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(p.getOrderID());
            }

            if (tt3 != null) {
                tt3.setText(p.getStatus());
            }
        }

        return v;
    }

}