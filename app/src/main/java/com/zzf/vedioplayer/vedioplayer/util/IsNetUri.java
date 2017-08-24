package com.zzf.vedioplayer.vedioplayer.util;

/**
 * Created by zzf on 2017/8/24.
 */

public class IsNetUri {
    public static boolean isNetUri(String uri){
        if(uri != null){
            if (uri.toLowerCase().startsWith("http")||uri.toLowerCase().startsWith("rtps")||uri.toLowerCase().startsWith("mms")){
                return true;
            }
            return false;
        }
        return false;
    }
}
