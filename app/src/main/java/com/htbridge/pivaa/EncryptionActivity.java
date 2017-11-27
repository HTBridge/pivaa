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
import android.widget.TextView;

import com.htbridge.pivaa.handlers.Encryption;
import com.htbridge.pivaa.handlers.MenuHandler;

public class EncryptionActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(EncryptionActivity.this, item.getItemId());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Hashing
        Button mHashingButton = (Button) findViewById(R.id.button_hashing);
        mHashingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Clicked hashing button");

                EditText mValueForHashingView = (EditText) findViewById(R.id.value_hashing);
                EditText mValueForHashingResultView = (EditText) findViewById(R.id.hashing_result);

                String value = mValueForHashingView.getText().toString();
                String hashingResult = Encryption.hash("MD5", value);

                mValueForHashingResultView.setText(hashingResult, TextView.BufferType.EDITABLE);
            }

        });

        // RNG
        Button mRNGButton = (Button) findViewById(R.id.button_rng);
        mRNGButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Clicked rng button");

                EditText mRNGResultView = (EditText) findViewById(R.id.rng_result);

                String rngResult = Encryption.rng();

                mRNGResultView.setText(rngResult, TextView.BufferType.EDITABLE);
            }

        });

        // Encryption AES/ECB/PKCS5Padding
        Button mEncryptionButton = (Button) findViewById(R.id.button_encryption);
        mEncryptionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Clicked encrypt button");

                EditText mEncryptionPlaintextView = (EditText) findViewById(R.id.plaintext_encryption);
                EditText mEncryptionCipherView = (EditText) findViewById(R.id.cipher_encryption);
                EditText mDecryptionCipherView = (EditText) findViewById(R.id.cipher_decryption);

                String value = mEncryptionPlaintextView.getText().toString();
                String encryptionResult = Encryption.encryptAES_ECB_PKCS5Padding(value);

                mEncryptionCipherView.setText(encryptionResult, TextView.BufferType.EDITABLE);
                mDecryptionCipherView.setText(encryptionResult, TextView.BufferType.EDITABLE);
            }

        });


        // Decryption AES/CBC/PKCS5Padding
        Button mWeakIVButton = (Button) findViewById(R.id.button_weak_iv);
        mWeakIVButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Clicked weak IV button");

                EditText mWeakIVCiphertView = (EditText) findViewById(R.id.cipher_weak_iv);
                EditText mWeakIVPlaintextView = (EditText) findViewById(R.id.plaintext_weak_iv);

                String value = mWeakIVPlaintextView.getText().toString();
                String encryptionResult = Encryption.encryptAES_CBC_PKCS5Padding(value);

                mWeakIVCiphertView.setText(encryptionResult, TextView.BufferType.EDITABLE);
            }

        });
    }

}
