package com.zzf.vedioplayer.vedioplayer.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzf.vedioplayer.vedioplayer.R;

/**
 * Created by zzf on 2017/8/18.
 */

public class Title extends LinearLayout implements View.OnClickListener {
    private TextView search_view;
    private ImageView history_view;
    private Context context;
    public Title(Context context) {
        this(context,null);
    }

    public Title(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Title(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        search_view = (TextView) getChildAt(0);
        history_view = (ImageView) getChildAt(1);

        search_view.setOnClickListener(this);
        history_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_bar:

                break;
            case R.id.history_image:
                break;
        }
    }
}
