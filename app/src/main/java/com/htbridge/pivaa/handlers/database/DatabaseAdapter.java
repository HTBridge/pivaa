package com.htbridge.pivaa.handlers.database;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.htbridge.pivaa.DatabaseActivity;
import com.htbridge.pivaa.R;

import java.util.ArrayList;

public class DatabaseAdapter extends ArrayAdapter<DatabaseRecord> implements View.OnClickListener{
    private ArrayList<DatabaseRecord> dataSet;
    Context mContext;
    DatabaseActivity activityRef;
    DatabaseHelper db;

    // View lookup cache
    private static class ViewHolder {
        TextView title_database_item;
        TextView author_database_item;
        Button button_delete_database_item;
    }

    public DatabaseAdapter(ArrayList<DatabaseRecord> data, Context context, DatabaseHelper db, DatabaseActivity ref) {
        super(context, R.layout.database_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.db = db;
        this.activityRef = ref;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DatabaseRecord dataModel = (DatabaseRecord) object;

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
        final DatabaseRecord dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.database_item, parent, false);

            viewHolder.title_database_item = (TextView) convertView.findViewById(R.id.title_database_item);
            viewHolder.author_database_item = (TextView) convertView.findViewById(R.id.author_database_item);
            viewHolder.button_delete_database_item = (Button) convertView.findViewById(R.id.button_delete_database_item);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        viewHolder.title_database_item.setText(dataModel.getTitle());
        viewHolder.author_database_item.setText(dataModel.getAuthor());


        viewHolder.button_delete_database_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("htbridge", "delete button clicked");
                db.deleteRecord(dataModel);
                activityRef.renderListView();
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }


}
