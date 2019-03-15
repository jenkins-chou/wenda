package com.w.wenda.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.w.wenda.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends AppCompatActivity {

    private String url;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tips)
    TextView tips;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initData();
    }

    void initData(){
        webView.getSettings().setJavaScriptEnabled(true);
        //允许android调用javascript
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress>=100){
                    if (!isFinishing()&&tips!=null){
                        tips.setVisibility(View.GONE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        url = getIntent()!=null?getIntent().getStringExtra("url"):"";
        if(url!=null&&!url.equals("")){
            webView.loadUrl(url);
        }
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
