package org.growingneighbors.growingneighbors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    ProgressDialog pd;
    //String homeURL = "https://gn.neilbrommer.com/"; // test page
    String homeURL = "https://growingneighbors.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get WebView object from .xml file
        wv = (WebView)findViewById(R.id.webview);

        // enable java script on the web view
        wv.getSettings().setJavaScriptEnabled(true);

        // set WebViewClient object with some custom methods
        wv.setWebViewClient(new WebViewClient()
        {
            // when loading started
            @Override
            public void onPageStarted(WebView view, String url, Bitmap icon) {
                super.onPageStarted(view, url, icon);
                // check internet connection
                if (Common.isConnectionAvailable(MainActivity.this)) {
                    // internet available -> show loading msg
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();
                } else {
                    // no internet -> show error msg
                    Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                    intent.putExtra("Error", "Please Check Internet Connection..");
                    startActivity(intent);
                    finish();
                }
            }

            // when loading finished
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // dismiss the loading msg on screen
                if (pd != null)
                    pd.dismiss();
            }

            // when other error received
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                intent.putExtra("Error", "Error : " + error.toString());
                startActivity(intent);
                finish();
            }

            // when click other links to load
            //// NEED TO TEST whether all the pages work properly
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains(homeURL)) {
                    // parts of our site -> open it on the same webview
                    view.loadUrl(request.getUrl().toString());
                    return false;
                } else {
                    // outer links -> open it with default internet browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    startActivity(intent);
                    return true;
                }
            }
        });

        // open home page
        wv.loadUrl(homeURL);
    }

    // when back button pressed
    @Override
    public void onBackPressed() {
        if (wv.canGoBack())
            wv.goBack(); // go back to the previous page
        else
            super.onBackPressed(); // no previous page -> exit the app
    }
}
