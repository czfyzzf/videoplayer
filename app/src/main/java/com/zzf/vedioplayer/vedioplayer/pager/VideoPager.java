package com.zzf.vedioplayer.vedioplayer.pager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zzf.vedioplayer.vedioplayer.R;
import com.zzf.vedioplayer.vedioplayer.base.BasePager;
import com.zzf.vedioplayer.vedioplayer.base.MyrecycleAdapter;
import com.zzf.vedioplayer.vedioplayer.entity.MediaItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zzf on 2017/8/17.
 */

public class VideoPager extends BasePager {

    private List<MediaItem> mediaItemList;
    private TextView video_text;
    private RecyclerView videoList;
    private ProgressBar video_pb;
    private Cursor cursor;
    private Handler handler;

    public VideoPager(Context context) {
        super(context);
    }

    @Override
    public View ininView() {
        View view = View.inflate(context, R.layout.video,null);
        video_text = (TextView) view.findViewById(R.id.video_text);
        videoList = (RecyclerView) view.findViewById(R.id.video_list);
        video_pb = (ProgressBar) view.findViewById(R.id.video_pb);
        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        mediaItemList = new ArrayList<>();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                video_pb.setVisibility(View.GONE);
                if (mediaItemList != null && mediaItemList.size()>0){
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    videoList.setLayoutManager(manager);
                    MyrecycleAdapter adapter = new MyrecycleAdapter(context,mediaItemList);
                    videoList.setAdapter(adapter);
                }else{
                    video_text.setVisibility(View.VISIBLE);
                }
            }
        };
        findLocalVideo();
    }

    private void findLocalVideo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                ContentResolver reslover = context.getContentResolver();//使用内容解析者获取视频资源
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA,//视频的绝对地址
                        MediaStore.Video.Media.ARTIST
                };
                cursor = reslover.query(uri,objs,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        long duration = cursor.getLong(1);
                        long size = cursor.getLong(2);
                        String data = cursor.getString(3);
                        String artist = cursor.getString(4);
                        MediaItem mediaItem = new MediaItem();
                        mediaItem.setName(name);
                        mediaItem.setDuration(duration);
                        mediaItem.setSize(Formatter.formatFileSize(context,size));
                        mediaItem.setData(data);
                        mediaItem.setArtist(artist);
                        mediaItemList.add(mediaItem);
                    }
                    cursor.close();
                }
                handler.sendEmptyMessage(10);
            }
        }.start();

    }
}
