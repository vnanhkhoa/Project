package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://doan.epizy.com/?i=1");
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
