package com.example.yls.newclient;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by yls on 2017/6/26.
 */
public class GuideActivity extends BaseActivity{
    private Button btnEnter;
    private ImageView iv01;
    private int count=0;
    private MediaPlayer mMediaPlayer;
    private boolean mExitActivity=false;
    private int[] imageArray=new int[]{
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t8,
    };

    private void startAnimation() {

        count=count%imageArray.length;
        iv01.setBackgroundResource(imageArray[count]);
        count ++;
        iv01.setAlpha(1f);
        iv01.animate()
                 .alpha(0.5f)
                .setDuration(2000)
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mExitActivity) {
                    startAnimation();
                }

            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    public void initListener() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
                enterHomeActivity();
            }
        });
    }

    private void enterHomeActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        btnEnter= (Button) findViewById(R.id.btn_01);
        iv01= (ImageView) findViewById(R.id.iv_01);
        startAnimation();
        playMusic();
    }

    @Override
    public void initData() {
        startAnimation();

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide;
    }
    protected void onDestroy(){
        super.onDestroy();
        mExitActivity=true;
        stopMusic();
    }
    private void playMusic(){
        try {
            mMediaPlayer=MediaPlayer.create(this,R.raw.new_version);

        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(1.0f,1.0f);
        mMediaPlayer.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    protected  void stopMusic(){
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
}
