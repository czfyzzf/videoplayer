package com.zzf.vedioplayer.vedioplayer.player;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zzf.vedioplayer.vedioplayer.R;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView playView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        playView = (VideoView) findViewById(R.id.video_view);

        Uri uri = getIntent().getData();
        playView.setVideoURI(uri);
        //准备好的监听
        playView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playView.start();
            }
        });
        //出错的监听
        playView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        //播放完成的监听
        playView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

        playView.setMediaController(new MediaController(VideoPlayActivity.this));
    }

}
