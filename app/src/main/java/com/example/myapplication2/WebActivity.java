package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WebActivity extends AppCompatActivity {

    public WebView mWebView;
    /* access modifiers changed from: private */
    public ProgressBar progressBarT4 = null;
    private View rootView = null;
    /* access modifiers changed from: private */
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);

        this.mWebView = (WebView) findViewById(R.id.webView1);
        this.mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.progressBarT4 = (ProgressBar) findViewById(R.id.progressBar4);
        this.progressBarT4.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar));
        this.mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBarT4.setVisibility(View.VISIBLE);
                progressBarT4.setProgress(progress);
                if (progress == 100) {
                    progressBarT4.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.mWebView.loadUrl(getIntent().getExtras().getString("link"));
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        ((FloatingActionButton) findViewById(R.id.floatingActionButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("text/plain");
                share.putExtra("android.intent.extra.TEXT", url);
                startActivity(Intent.createChooser(share, "Поделиться новостью"));
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient());
    }
}
