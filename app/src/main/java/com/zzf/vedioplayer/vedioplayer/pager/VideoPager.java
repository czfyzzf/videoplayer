package com.zzf.vedioplayer.vedioplayer.pager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zzf.vedioplayer.vedioplayer.base.BasePager;


/**
 * Created by zzf on 2017/8/17.
 */

public class VideoPager extends BasePager {
    public VideoPager(Context context) {
        super(context);
    }

    @Override
    public View ininView() {
        TextView textView = new TextView(context);
        textView.setText("video");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }
}
