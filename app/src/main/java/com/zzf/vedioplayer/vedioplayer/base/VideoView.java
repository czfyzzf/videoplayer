package com.zzf.vedioplayer.vedioplayer.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by zzf on 2017/8/20.
 */

public class VideoView extends android.widget.VideoView {
    public VideoView(Context context) {
            this(context,null);
        }

        public VideoView(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void SetVideoSize(int videowidth,int videoheigt){
            ViewGroup.LayoutParams params = getLayoutParams();
            params.width = videowidth;
            params.height = videoheigt;
            setLayoutParams(params);
        }
    }

