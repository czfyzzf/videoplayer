package com.zzf.vedioplayer.vedioplayer.util;

import android.content.Context;
import android.net.TrafficStats;

/**
 * Created by zzf on 2017/8/24.
 */

public class Utils {
    public static String netSpeed(Context context) {
        long lastTimeStamp = 0;
        long lastTotalRxBytes = 0;

        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        return  String.valueOf(speed) + " kb/s";
    }
}

