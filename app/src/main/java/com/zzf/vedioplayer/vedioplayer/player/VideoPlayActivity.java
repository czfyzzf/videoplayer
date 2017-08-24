package com.zzf.vedioplayer.vedioplayer.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zzf.vedioplayer.vedioplayer.R;
import com.zzf.vedioplayer.vedioplayer.base.VideoView;
import com.zzf.vedioplayer.vedioplayer.entity.MediaItem;
import com.zzf.vedioplayer.vedioplayer.util.IsNetUri;
import com.zzf.vedioplayer.vedioplayer.util.TimeTransfer;
import com.zzf.vedioplayer.vedioplayer.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PROGRESS = 1;
    private static final int SYSTEMTIME = 2;
    private static final String TAG = "VideoPlayActivity.class";
    private static final int HIDECONTROLLER = 3;
    private static final int NET_SPEED = 4;
    private VideoView playView;
    private TextView videoName;
    private ImageView battaryStuta;
    private TextView systemTime;
    private Button vioceClose;
    private SeekBar voiceSeekbar;
    private Button otherPaly;
    private TextView currentTime;
    private SeekBar videoSeekbar;
    private TextView videoAllTime;
    private Button videoExit;
    private Button playBack;
    private Button videoPlayPause;
    private Button screenType;
    private Handler handler;
    private BatteryReceiver receiver;
    private Button playNext;
    private int position;
    private ArrayList<MediaItem> videoList;
    private GestureDetector gestureDetector;
    private boolean isHideController;
    private RelativeLayout mediaController;
    private boolean isfullscreen = false;
    private int videoHight;
    private int videoWidth;
    private AudioManager audioManager;
    private int maxVoice;
    private int currentVoice;
    private boolean isMust = false;
    private WindowManager windowManager;
    private boolean isNetUri;
    private LinearLayout ll_buffer;
    private boolean isSystemInfo = false;
    private int lastTime = 0;
    private LinearLayout startBuffer;
    private TextView netSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        findViews();

        videoList = new ArrayList<>();
        videoSeekbar.setClickable(true);
        setListener();
        setDate();
        setVideoSeekbar();
        setVideoTitle();
       // Log.i(TAG, String.valueOf(videoList.size() == 0) + "5555555555555");

        receiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    private void hideMediaController() {
        mediaController.setVisibility(View.GONE);
        isHideController = true;
    }

    private void showMediaController() {
        mediaController.setVisibility(View.VISIBLE);
        isHideController = false;
    }

    private void setListener() {
        //准备好的监听
        playView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                startBuffer.setVisibility(View.GONE);
                videoWidth = mp.getVideoWidth();
                videoHight = mp.getVideoHeight();
                playView.start();
                changeButtonState();
                hideMediaController();
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

        // playView.setMediaController(new MediaController(VideoPlayActivity.this));

        videoSeekbar.setOnSeekBarChangeListener(new MyVedioOnSeekBarChangeListener());
        vioceClose.setOnClickListener(this);
        otherPaly.setOnClickListener(this);
        videoExit.setOnClickListener(this);
        playBack.setOnClickListener(this);
        videoPlayPause.setOnClickListener(this);
        screenType.setOnClickListener(this);
        playNext.setOnClickListener(this);
        voiceSeekbar.setOnSeekBarChangeListener(new MyVoiceOnSeekbarChangeListen());
        if(isSystemInfo && playView.isPlaying()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                playView.setOnInfoListener(new MyOnInfoListener());
            }
        }
    }

    private void setVideoTitle() {
        videoName.setText(videoList.get(position).getName());
        handler.sendEmptyMessage(SYSTEMTIME);
    }

    private void setDate() {
        Uri uri = getIntent().getData();
        videoList = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position", 0);
        if (videoList != null && videoList.size() > 0) {
            isNetUri = IsNetUri.isNetUri(videoList.get(position).getData());
            playView.setVideoPath(videoList.get(position).getData());
        } else if (uri != null) {
            isNetUri = IsNetUri.isNetUri(uri.toString());
            playView.setVideoURI(uri);
        } else {
            Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void setVideoSeekbar() {
        long videoTime = videoList.get(position).getDuration();
        videoSeekbar.setMax((int) videoTime);
        videoAllTime.setText(TimeTransfer.transferLongToDate("mm:ss", videoTime));

        handler.sendEmptyMessage(PROGRESS);
    }

    private void findViews() {
        playView = (VideoView) findViewById(R.id.video_view);
        videoName = (TextView) findViewById(R.id.video_name);
        battaryStuta = (ImageView) findViewById(R.id.battary_stuta);
        systemTime = (TextView) findViewById(R.id.system_time);
        vioceClose = (Button) findViewById(R.id.vioce_close);
        voiceSeekbar = (SeekBar) findViewById(R.id.voice_seekbar);
        otherPaly = (Button) findViewById(R.id.other_paly);
        currentTime = (TextView) findViewById(R.id.current_time);
        videoSeekbar = (SeekBar) findViewById(R.id.video_seekbar);
        videoAllTime = (TextView) findViewById(R.id.video_all_time);
        videoExit = (Button) findViewById(R.id.video_exit);
        playBack = (Button) findViewById(R.id.video_back);
        videoPlayPause = (Button) findViewById(R.id.video_play_pause);
        screenType = (Button) findViewById(R.id.screen_type);
        playNext = (Button) findViewById(R.id.play_next);
        ll_buffer = (LinearLayout) findViewById(R.id.net_info);
        netSpeed = (TextView) findViewById(R.id.net_speed);
        startBuffer = (LinearLayout) findViewById(R.id.buffer_info);

        netSpeed.setText("正在加载中..."+ Utils.netSpeed(VideoPlayActivity.this));
        handler.sendEmptyMessage(NET_SPEED);

        gestureDetector = new GestureDetector(this, new MyOnGestureListener());
        mediaController = (RelativeLayout) findViewById(R.id.media_controller);
        audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        windowManager = getWindowManager();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HIDECONTROLLER:
                        hideMediaController();
                        break;
                    case NET_SPEED:
                        netSpeed.setText("正在加载中..."+Utils.netSpeed(VideoPlayActivity.this));
                        handler.removeMessages(NET_SPEED);
                        handler.sendEmptyMessageDelayed(NET_SPEED,3*1000);
                        break;
                    case PROGRESS:
                        int currenttime = playView.getCurrentPosition();
                        videoSeekbar.setProgress(currenttime);

                        if(isSystemInfo && playView.isPlaying()){
                            if(isSystemInfo) {
                                if ((currenttime - lastTime) > 500) {
                                    //卡了
                                    ll_buffer.setVisibility(View.VISIBLE);
                                } else {
                                    //不卡
                                    ll_buffer.setVisibility(View.GONE);
                                }
                            }
                        }
                        lastTime =currenttime;

                        if(isNetUri){
                            int buffer = playView.getBufferPercentage();
                            int totalBuffer = buffer * videoSeekbar.getMax();
                            int seconedProgress = totalBuffer/100;
                            videoSeekbar.setSecondaryProgress(seconedProgress);
                        }
                        currentTime.setText(TimeTransfer.transferLongToDate("mm:ss", (long) currenttime));
                        handler.removeMessages(PROGRESS);
                        handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                        break;
                    case SYSTEMTIME:
                        systemTime.setText(getSystemTime());

                        handler.removeMessages(SYSTEMTIME);
                        handler.sendEmptyMessageDelayed(SYSTEMTIME, 60 * 1000);
                        break;
                }
            }
        };
        maxVoice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVoice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        voiceSeekbar.setMax(maxVoice);
        voiceSeekbar.setProgress(currentVoice);
    }

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Log.i(TAG, String.valueOf(TextUtils.isEmpty(format.format(new Date()))) + "++++++++");
        return format.format(new Date());
    }

    @Override
    public void onClick(View v) {
        if (v == vioceClose) {
            // Handle clicks for vioceClose
            isMust = (!isMust);
            upDateVoice(currentVoice,isMust);
        } else if (v == otherPaly) {
            // Handle clicks for otherPaly
        } else if (v == videoExit) {
            finish();
        } else if (v == playBack) {
            // Handle clicks for videoBack
            startBuffer.setVisibility(View.VISIBLE);
            position--;
            changeButtonState();
            MediaItem mediaItem = videoList.get(position);
            playView.setVideoPath(mediaItem.getData());
            isNetUri = IsNetUri.isNetUri(mediaItem.getData());
            playView.start();
        } else if (v == playNext) {
            startBuffer.setVisibility(View.VISIBLE);
            position++;
            changeButtonState();
            MediaItem mediaItem = videoList.get(position);
            isNetUri = IsNetUri.isNetUri(mediaItem.getData());
            playView.setVideoPath(mediaItem.getData());
            playView.start();
        } else if (v == videoPlayPause) { //暂停和播放
            // Handle clicks for videoPlayPause
            if (playView.isPlaying()) {
                playView.pause();
                videoPlayPause.setBackgroundResource(R.drawable.video_play_selsector);
            } else {
                playView.start();
                videoPlayPause.setBackgroundResource(R.drawable.video_pasue_selsector);
            }
        } else if (v == screenType) {
            // Handle clicks for screenType
            if(isfullscreen){
                isfullscreen = false;
                playView.SetVideoSize(playView.getWidth(),playView.getHeight());
                screenType.setBackgroundResource(R.drawable.screen_type_full_selector);
            }else{
                DisplayMetrics dm = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;//屏幕的宽高
                int height = dm.heightPixels;
                int mVideoWidth = videoWidth;//视频的宽高
                int mVideoHeight = videoHight;

                if ( mVideoWidth * height  < width * mVideoHeight ) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                }
                isfullscreen = true;
                playView.SetVideoSize(width,height);
                playView.setBackgroundResource(R.drawable.screen_type_original_selector);
            }
        }

        handler.removeMessages(HIDECONTROLLER);
    }

    private void changeButtonState() {
        if (videoList.size() == 1) {
            playNext.setClickable(false);
            playNext.setBackgroundResource(R.drawable.video_next_btn_bg);
            playBack.setClickable(false);
            playBack.setBackgroundResource(R.drawable.video_pre_gray);
        } else if (videoList.size() > 1) {
            if (position == 0) {
                playBack.setClickable(false);
                playBack.setBackgroundResource(R.drawable.video_pre_gray);
                playNext.setClickable(true);
                playNext.setBackgroundResource(R.drawable.video_btn_forward_selector);
            } else if (position == videoList.size() - 1) {
                playNext.setClickable(false);
                playNext.setBackgroundResource(R.drawable.video_next_btn_bg);
                playBack.setClickable(true);
                playBack.setBackgroundResource(R.drawable.video_pre_selector);
            } else {
                playBack.setClickable(true);
                playBack.setBackgroundResource(R.drawable.video_pre_selector);
                playNext.setClickable(true);
                playNext.setBackgroundResource(R.drawable.video_btn_forward_selector);
            }
        }
    }

    private void setBattery(int level) {
        if (level <= 10) {
            battaryStuta.setImageResource(R.drawable.ic_battery_0);
        } else if (level <= 20) {
            battaryStuta.setImageResource(R.drawable.ic_battery_20);
        } else if (level <= 40) {
            battaryStuta.setImageResource(R.drawable.ic_battery_40);
        } else if (level <= 60) {
            battaryStuta.setImageResource(R.drawable.ic_battery_60);
        } else if (level <= 80) {
            battaryStuta.setImageResource(R.drawable.ic_battery_80);
        } else if (level <= 100) {
            battaryStuta.setImageResource(R.drawable.ic_battery_100);
        }
    }

    class MyVedioOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                playView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDECONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
        }
    }

    public void upDateVoice(int progress,boolean Must) {
        int lastProgress = progress;
        Log.i(TAG, String.valueOf(lastProgress));
        if(Must){
            voiceSeekbar.setProgress(0);
            currentVoice = 0;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
        }else{
            voiceSeekbar.setProgress(15);
            currentVoice = 15;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currentVoice,0);
        }
        if(currentVoice > 0){
            isMust = false;
        }else{
            isMust = true;
        }
    }

    class MyVoiceOnSeekbarChangeListen implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                isMust = false;
                upDateVoice(progress,isMust);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDECONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            setBattery(level);
        }
    }

    class MyOnInfoListener implements MediaPlayer.OnInfoListener {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what){
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    ll_buffer.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    ll_buffer.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        public MyOnGestureListener() {
            super();
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(isHideController){
                showMediaController();
                handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
            }else{
                hideMediaController();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distance = e1.getY() - e2.getY();
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
            float heightPixels = dm.heightPixels;
            if(distance < 0){//向下滑，distance小于零
                int changeVoice = (int) ((distance/heightPixels)*maxVoice);
                currentVoice = currentVoice + changeVoice;
                voiceSeekbar.setProgress(currentVoice);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currentVoice,0);
            }
            if(distance > 0){//向下滑，distance小于零
                int changeVoice = (int) ((distance/heightPixels)*maxVoice);
                currentVoice = currentVoice + changeVoice;
                voiceSeekbar.setProgress(currentVoice);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currentVoice,0);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (playView.isPlaying()) {
                playView.pause();
                videoPlayPause.setBackgroundResource(R.drawable.video_play_selsector);
            } else {
                playView.start();
                videoPlayPause.setBackgroundResource(R.drawable.video_pasue_selsector);
            }
            return super.onDoubleTap(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            currentVoice --;
            voiceSeekbar.setProgress(currentVoice);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currentVoice,0);
            handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            currentVoice ++;
            voiceSeekbar.setProgress(currentVoice);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currentVoice,0);
            handler.sendEmptyMessageDelayed(HIDECONTROLLER,4000);
        }
        return super.onKeyDown(keyCode, event);
    }
}