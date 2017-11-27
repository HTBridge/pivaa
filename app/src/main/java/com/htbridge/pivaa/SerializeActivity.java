package com.htbridge.pivaa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htbridge.pivaa.handlers.MenuHandler;
import com.htbridge.pivaa.handlers.ObjectSerialization;
import com.htbridge.pivaa.handlers.database.DatabaseRecord;

import java.util.ServiceLoader;

public class SerializeActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(SerializeActivity.this, item.getItemId());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String serializableFileLocation = this.getExternalFilesDir(null) + "/serializable.dat";


        // Hooking save
        Button mSaveSerializeButton = (Button) findViewById(R.id.button_save_serialize);
        mSaveSerializeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mInputSerializeView = (EditText) findViewById(R.id.input_serialize);
                String input = mInputSerializeView.getText().toString();

                ObjectSerialization obj = new ObjectSerialization(serializableFileLocation);
                obj.setProof(input);
                obj.saveObject();
            }

        });

        // Hooking load
        Button mLoadSerializeButton = (Button) findViewById(R.id.button_load_serialize);
        mLoadSerializeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ObjectSerialization obj = new ObjectSerialization(serializableFileLocation);
                obj.loadObject();
                String proof = obj.getProof();
                Toast.makeText(getApplicationContext(), proof, Toast.LENGTH_SHORT).show();

                EditText mInputSerializeView = (EditText) findViewById(R.id.input_serialize);
                mInputSerializeView.setText(proof, TextView.BufferType.EDITABLE);
            }

        });
    }

}
