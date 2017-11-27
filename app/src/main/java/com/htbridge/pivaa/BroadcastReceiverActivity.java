package com.htbridge.pivaa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.htbridge.pivaa.handlers.Authentication;
import com.htbridge.pivaa.handlers.MenuHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class BroadcastReceiverActivity extends AppCompatActivity {
    private WebView myWebView;
    private String location;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(BroadcastReceiverActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myWebView = (WebView) findViewById(R.id.webview_broadcast_receiver);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setWebChromeClient(new WebChromeClient());

        // setting up configuration for WebView
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        location = this.getExternalFilesDir(null) + "/broadcast.html";
        myWebView.loadUrl("file://" + location);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(location, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println("Your new file");
            pw.flush();
            pw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        // send broadcast
        Button mWebviewButtonLink1 = (Button) findViewById(R.id.button_broadcast_receiver);
        mWebviewButtonLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mBroadcastInputView = (EditText) findViewById(R.id.input_broadcast_receiver);
                String input_broadcast = mBroadcastInputView.getText().toString();


                Intent intent = new Intent();
                intent.setAction("service.vulnerable.vulnerableservice.LOG");
                intent.putExtra("data", input_broadcast);
                intent.putExtra("location", location);

                sendBroadcast(intent);

                // refresh webview
                try {
                    Thread.sleep(300);
                    myWebView.loadUrl(("file://" + location));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });



    }


}
