package com.zzf.vedioplayer.vedioplayer.UI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.zzf.vedioplayer.vedioplayer.R;

public class splashActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                starMainActivity();
            }
        },2000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        starMainActivity();
        return super.onTouchEvent(event);
    }

    private void starMainActivity() {
        Intent intent = new Intent(splashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
