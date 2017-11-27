package com.htbridge.pivaa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.htbridge.pivaa.handlers.about.AboutAdapter;
import com.htbridge.pivaa.handlers.about.AboutJSONParser;
import com.htbridge.pivaa.handlers.about.AboutRecord;
import com.htbridge.pivaa.handlers.MenuHandler;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(AboutActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        renderListView();
    }

    /**
     * Render custom item
     */
    public void renderListView() {
        // get all records

        AboutJSONParser aboutJSONParser = new AboutJSONParser("vulnerabilities.json", getApplicationContext());
        ArrayList<AboutRecord> list = aboutJSONParser.parse();

        AboutAdapter adapter = new AboutAdapter(list, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.listview_about);
        listView.setFocusable(false);
        listView.setAdapter(adapter);

        this.setListViewHeightBasedOnChildren(listView, adapter);
    }


    /**
     * Shrink listview height
     * @param listView
     * @param adapter
     */
    public void setListViewHeightBasedOnChildren(ListView listView, AboutAdapter adapter) {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + 180;
            Log.i("htbridge", "listItem.getMeasuredHeight()  = " + listItem.getMeasuredHeight() );
        }

        Log.i("htbridge", "totalHeight = " + totalHeight);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}
