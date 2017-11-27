package com.htbridge.pivaa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.htbridge.pivaa.handlers.MenuHandler;

public class WebviewActivity extends AppCompatActivity {
    private WebView myWebView;
    private Configuration config;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(WebviewActivity.this, item.getItemId());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setWebChromeClient(new WebChromeClient());

        // setting up configuration for WebView
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowUniversalAccessFromFileURLs(true);


        // loading configuration URL and showing on UI
        config = new Configuration();


        // URL navigate
        Button mWebviewButton = (Button) findViewById(R.id.button_webview);
        mWebviewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mWebviewURLView = (EditText) findViewById(R.id.url_webview);
                String url = mWebviewURLView.getText().toString();
                myWebView.loadUrl(url);
            }

        });

        // LINK 1 navigate
        Button mWebviewButtonLink1 = (Button) findViewById(R.id.button_webview_link1);
        mWebviewButtonLink1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mWebviewURLView = (EditText) findViewById(R.id.url_webview);
                myWebView.loadUrl(config.url_webview_link_1);
                mWebviewURLView.setText(config.url_webview_link_1, TextView.BufferType.EDITABLE);
            }

        });

        // LINK 2 navigate
        Button mWebviewButtonLink2 = (Button) findViewById(R.id.button_webview_link2);
        mWebviewButtonLink2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mWebviewURLView = (EditText) findViewById(R.id.url_webview);
                myWebView.loadUrl(config.url_webview_link_2);
                mWebviewURLView.setText(config.url_webview_link_2, TextView.BufferType.EDITABLE);
            }

        });

        // LINK 3 navigate
        Button mWebviewButtonLink3 = (Button) findViewById(R.id.button_webview_link3);
        mWebviewButtonLink3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mWebviewURLView = (EditText) findViewById(R.id.url_webview);
                myWebView.loadUrl("http://google.com");
                mWebviewURLView.setText("http://google.com", TextView.BufferType.EDITABLE);
            }

        });

        // XSS me
        Button mXSSButton = (Button) findViewById(R.id.button_xss);
        mXSSButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("htbridge", "Clicked XSS button");
                myWebView.loadUrl("javascript:alert('Hello World!')");
            }

        });


        EditText mWebviewURLView = (EditText) findViewById(R.id.url_webview);
        mWebviewURLView.setText(config.url_webview, TextView.BufferType.EDITABLE);

        // opening url
        myWebView.loadUrl(config.url_webview);
    }

}
