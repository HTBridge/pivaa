package com.htbridge.pivaa;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.htbridge.pivaa.handlers.LoadCode;
import com.htbridge.pivaa.handlers.MenuHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class LoadCodeActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(LoadCodeActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_code);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load code
        Button mLoadcodeButton = (Button) findViewById(R.id.button_load_code);
        mLoadcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Load code button clicked");
                LoadCode loadCode = new LoadCode(getApplicationContext());
                String result = loadCode.load("com.htbridge.raw.ExternalCode");

                TextView mOutputLoadCodeView = (TextView) findViewById(R.id.output_load_code);
                mOutputLoadCodeView.setText(result, TextView.BufferType.EDITABLE);
            }
        });
    }


}
