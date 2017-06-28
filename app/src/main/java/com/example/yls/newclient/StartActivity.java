package com.example.yls.newclient;

import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by yls on 2017/6/26.
 */

public class StartActivity extends BaseActivity{
    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        new Thread(){
            public void run(){
                SystemClock.sleep(2000);
                enterGuideActivity();
            }
        }.start();

    }

    private void enterGuideActivity() {
        Intent intent=new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_start;
    }
}
