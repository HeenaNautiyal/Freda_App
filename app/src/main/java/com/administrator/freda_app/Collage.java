package com.administrator.freda_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class Collage extends AppCompatActivity {
    WebView webView;
    ProgressDialog pb;
    SwipeRefreshLayout mySwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_frames);
        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageButton imageButton= (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Collage.this, Home.class);
                startActivity(it);
            }
        });
        webView = (WebView) findViewById(R.id.web1);
        webView.setWebViewClient(new MyWebViewClient());
        pb = new ProgressDialog(Collage.this);
        pb.setMessage("Please wait while Loading...");
        pb.show();

        String url = "http://www.officialdiplomaframes.com/pages/college-frames";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:document.getElementById(\"header\").setAttribute(\"style\",\"display:none;\");");
                webView.loadUrl("javascript:(function() { " + "document.getElementsByClassName('header mm-fixed-top')[0].style.display='none'; })()");
                pb.dismiss();
                mySwipeRefreshLayout.setRefreshing(false);
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Collage.this);
                TextView myMsg = new TextView(Collage.this);
                myMsg.setText("Mobile Data is off");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                builder.setCustomTitle(myMsg);
                builder.setMessage("Turn on mobile data or use Wi-Fi to access data.");
                builder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                builder.setNegativeButton("Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        webView.loadUrl(url);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                    }
                }
        );
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
             webView.goBack();
        }
            super.onBackPressed();
    }
}
