package com.zzf.vedioplayer.vedioplayer.pager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zzf.vedioplayer.vedioplayer.base.BasePager;

/**
 * Created by zzf on 2017/8/17.
 */

public class NetVideoPager extends BasePager {
    public NetVideoPager(Context context) {
        super(context);
    }

    @Override
    public View ininView() {
        TextView textView = new TextView(context);
        textView.setText("netvideo");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }
}
