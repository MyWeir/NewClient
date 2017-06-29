package com.example.yls.newclient;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

/**
 * Created by yls on 2017/6/29.
 */

public class VideoActivity extends  BaseActivity {
    private VideoView videoView;
    private ProgressBar progressBar;
    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        videoView= (VideoView) findViewById(R.id.video_view);
        progressBar= (ProgressBar) findViewById(R.id.pb_01);

    }

    @Override
    public void initData() {
        String videoUrl=getIntent().getStringExtra("video_url");
        videoView.setVideoPath(videoUrl);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_play;

}
}
