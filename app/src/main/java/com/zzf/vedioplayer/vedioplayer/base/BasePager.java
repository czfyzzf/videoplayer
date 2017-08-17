package com.zzf.vedioplayer.vedioplayer.base;

import android.content.Context;
import android.view.View;

/**
 * Created by zzf on 2017/8/17.
 */

public abstract class BasePager {
    public View rootView;
    public final Context context;
    public boolean isCreadView ;
    public BasePager(Context context){
        this.context = context;
        rootView = ininView();
    }

    public abstract  View ininView();

    public void initDate(){

    };
}
