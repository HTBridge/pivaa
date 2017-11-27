package com.htbridge.pivaa.handlers.about;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.htbridge.pivaa.R;
import com.htbridge.pivaa.handlers.about.AboutRecord;

import java.util.ArrayList;

public class AboutAdapter extends ArrayAdapter<AboutRecord> implements View.OnClickListener {
    private ArrayList<AboutRecord> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView name_about_item;
        TextView description_about_item;
    }

    public AboutAdapter(ArrayList<AboutRecord> data, Context context) {
        super(context, R.layout.about_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        AboutRecord dataModel = (AboutRecord) object;

        Log.d("htbridge", "click");

        switch (v.getId()) {
            /*case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;*/
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final AboutRecord dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.about_item, parent, false);

            viewHolder.name_about_item = (TextView) convertView.findViewById(R.id.name_about_item);
            viewHolder.description_about_item = (TextView) convertView.findViewById(R.id.description_about_item);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        viewHolder.name_about_item.setText(dataModel.getName());
        viewHolder.description_about_item.setText(dataModel.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }


}
