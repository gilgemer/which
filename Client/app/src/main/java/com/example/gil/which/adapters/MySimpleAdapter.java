package com.example.gil.which.adapters;

/**
 * Created by gil on 13/04/17.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gil.which.R;
import com.example.gil.which.helpers.DownloadTask;
import com.example.gil.which.helpers.MLRoundedImageView;

public class MySimpleAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public MySimpleAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        new DownloadTask((MLRoundedImageView) vi.findViewById(R.id.userPhoto))
                .execute((String) data.get("userphoto"));

        TextView userName = (TextView) vi.findViewById(R.id.userName);
        userName.setText((String) data.get("username"));

        new DownloadTask((MLRoundedImageView) vi.findViewById(R.id.choice1))
                .execute((String) data.get("choice1"));
        new DownloadTask((MLRoundedImageView) vi.findViewById(R.id.choice2))
                .execute((String) data.get("choice2"));

        TextView Title1 = (TextView) vi.findViewById(R.id.title1);
        Title1.setText((String) data.get("title1"));

        TextView Title2 = (TextView) vi.findViewById(R.id.title2);
        Title2.setText((String) data.get("title2"));

        //VS String is defined as a literal in strings
        TextView VS = (TextView) vi.findViewById(R.id.vs);
        VS.setText(R.string.vs_name);

        TextView Timestamp = (TextView) vi.findViewById(R.id.timestamp);
        Timestamp.setText((String) data.get("timestamp"));


        return vi;
    }

}