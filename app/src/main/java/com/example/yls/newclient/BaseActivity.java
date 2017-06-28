package com.example.yls.newclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by yls on 2017/6/26.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
        initListener();
        initData();
    }

    public abstract void initListener() ;
    public  abstract void initView();
    public  abstract void initData() ;
    protected abstract  int getLayoutRes();
    private Toast mToast;
    public void showToast(String msg){
        if(mToast==null){
            mToast= Toast.makeText(this,"",Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
