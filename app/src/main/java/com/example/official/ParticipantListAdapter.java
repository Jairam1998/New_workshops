package com.example.official;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ParticipantListAdapter extends ArrayAdapter<ParticipantListItem> {

    private int resourceLayout;
    private Context mContext;

    public ParticipantListAdapter(Context context, int resource, List<ParticipantListItem> items) {
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

        ParticipantListItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            TextView tt2 = (TextView) v.findViewById(R.id.email);
            TextView tt3 = (TextView) v.findViewById(R.id.college);
            TextView tt4 = (TextView) v.findViewById(R.id.id);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(p.getEmail());
            }

            if (tt3 != null) {
                tt3.setText(p.getCollege().toString());
            }

            if (tt4 != null) {
                tt3.setText(p.getId().toString());
            }
        }

        return v;
    }

}