package com.htbridge.pivaa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.htbridge.pivaa.handlers.database.DatabaseAdapter;
import com.htbridge.pivaa.handlers.database.DatabaseHelper;
import com.htbridge.pivaa.handlers.database.DatabaseRecord;
import com.htbridge.pivaa.handlers.MenuHandler;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(DatabaseActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * CRUD Operations
         * */
        // add record
        Configuration config = new Configuration();
        db = new DatabaseHelper(this);
        db.initDatabaseOuter();
        db.addRecord(new DatabaseRecord(config.default_title_database_item, config.default_author_database_item));

        renderListView();


        // Hooking DB insert button
        Button mDatabaseInsertButton = (Button) findViewById(R.id.button_insert_database);
        mDatabaseInsertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mTitleDatabaseView = (EditText) findViewById(R.id.title_database);
                EditText mAuthorDatabaseView = (EditText) findViewById(R.id.author_database);


                String title = mTitleDatabaseView.getText().toString();
                String author = mAuthorDatabaseView.getText().toString();

                db.addRecord(new DatabaseRecord(title, author));

                renderListView();
            }

        });


        // Hooking DB insert button
        Button mDatabaseRawSQLButton = (Button) findViewById(R.id.button_raw_sql_database);
        mDatabaseRawSQLButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mTitleDatabaseView = (EditText) findViewById(R.id.raw_sql_database);
                String rawSQLText = mTitleDatabaseView.getText().toString();

                String result = db.rawSQLQuery(rawSQLText);
                Log.d("htbridge", result);

                TextView mOutputDatabaseView = (TextView) findViewById(R.id.output_database);
                mOutputDatabaseView.setText(result, TextView.BufferType.EDITABLE);

                renderListView();
            }

        });


        // delete one record
        //db.deleteRecord(list.get(0));

        // get all record
        //db.getAllRecords();

    }


    /**
     * Render custom item
     */
    public void renderListView() {
        // get all records
        ArrayList<DatabaseRecord> list = db.getAllRecords();
        DatabaseAdapter adapter = new DatabaseAdapter(list, getApplicationContext(), db, this);

        ListView listView = (ListView) findViewById(R.id.listview_database);
        listView.setAdapter(adapter);
    }

}
