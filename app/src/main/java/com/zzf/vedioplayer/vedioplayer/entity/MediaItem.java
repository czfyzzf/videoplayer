package com.zzf.vedioplayer.vedioplayer.entity;

import com.zzf.vedioplayer.vedioplayer.util.TimeTransfer;

import java.io.Serializable;


/**
 * Created by zzf on 2017/8/18.
 */

public class MediaItem implements Serializable{
    private String name;
    private long duration;
    private String size;
    private String data;
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
