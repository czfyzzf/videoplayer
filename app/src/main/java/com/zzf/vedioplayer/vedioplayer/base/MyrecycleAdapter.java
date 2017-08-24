package com.zzf.vedioplayer.vedioplayer.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzf.vedioplayer.vedioplayer.R;
import com.zzf.vedioplayer.vedioplayer.entity.MediaItem;
import com.zzf.vedioplayer.vedioplayer.player.VideoPlayActivity;
import com.zzf.vedioplayer.vedioplayer.util.TimeTransfer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzf on 2017/8/18.
 */

public class MyrecycleAdapter extends RecyclerView.Adapter<MyrecycleAdapter.ViewHolder> {
    public List<MediaItem> mediaItems;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View videoView;
        TextView mediaName;
        TextView mediaSize;
        TextView mediaDuration;

        public ViewHolder(View view) {
            super(view);
            videoView = view;
            mediaName = (TextView) view.findViewById(R.id.media_name);
            mediaSize = (TextView) view.findViewById(R.id.media_siaze);
            mediaDuration = (TextView) view.findViewById(R.id.media_duration);
        }
    }

    public MyrecycleAdapter(Context context,List<MediaItem> mediaItemList) {
        this.mediaItems = mediaItemList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mediaitem,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MediaItem mediaItem = mediaItems.get(position);
                Intent intent = new Intent(context, VideoPlayActivity.class);
//                intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist", (Serializable) mediaItems);
                intent.putExtras(bundle);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MediaItem mediaItem = mediaItems.get(position);
        holder.mediaName.setText(mediaItem.getName());
        holder.mediaDuration.setText(TimeTransfer.transferLongToDate("HH:mm:ss",mediaItem.getDuration()));
        holder.mediaSize.setText(mediaItem.getSize());
    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }
}
