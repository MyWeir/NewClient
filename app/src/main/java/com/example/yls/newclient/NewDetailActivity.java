package com.example.yls.newclient;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import url.NewEntity;

/**
 * Created by yls on 2017/6/28.
 */

public class NewDetailActivity extends BaseActivity {
  private WebView wedView;
    private ProgressBar progressBar;
    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        progressBar= (ProgressBar) findViewById(R.id.pb_01);
        initWedView();
    }

    private void initWedView() {
        wedView= (WebView) findViewById(R.id.web_view);
        wedView.setWebViewClient(new WebViewClient());
        wedView.getSettings().setJavaScriptEnabled(true);
        wedView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view,int newProgress){
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                    System.out.println("------percent:"+newProgress);
                }
            }
        });
    }

    @Override
    public void initData() {
        NewEntity.ResultBean newBean= (NewEntity.ResultBean) getIntent().getSerializableExtra("news");
        wedView.loadUrl(newBean.getUrl());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(newBean.getTitle());
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news_detail;
    }
}
