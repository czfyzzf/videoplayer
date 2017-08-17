package com.zzf.vedioplayer.vedioplayer.UI;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.zzf.vedioplayer.vedioplayer.R;
import com.zzf.vedioplayer.vedioplayer.RepalaceFragment;
import com.zzf.vedioplayer.vedioplayer.base.BasePager;
import com.zzf.vedioplayer.vedioplayer.pager.MusicPager;
import com.zzf.vedioplayer.vedioplayer.pager.NetMusicPager;
import com.zzf.vedioplayer.vedioplayer.pager.NetVideoPager;
import com.zzf.vedioplayer.vedioplayer.pager.VideoPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
private FrameLayout frameLayout;
    private RadioGroup rg_bottom;
    private List<BasePager> pagrList;
    private int currentPosition;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.frame_content);
        rg_bottom = (RadioGroup) findViewById(R.id.rg_main_bottom);

        pagrList = new ArrayList<>();
        pagrList.add(new VideoPager(this));
        pagrList.add(new MusicPager(this));
        pagrList.add(new NetVideoPager(this));
        pagrList.add(new NetMusicPager(this));

        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    default:
                        currentPosition = 0;
                        break;
                    case R.id.rg_main_bottom_music:
                        currentPosition = 1;
                        break;
                    case R.id.rg_main_bottom_net_video:
                        currentPosition = 2;
                        break;
                    case R.id.rg_main_bottom_net_music:
                        currentPosition = 3;
                        break;
                }
                setFragment();
            }
        });
        rg_bottom.check(R.id.rg_main_bottom_video);



    }

    private void setFragment() {
        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transaction = manage.beginTransaction();
        transaction.replace(R.id.frame_content,new RepalaceFragment(getbasePager()));;
        transaction.commit();
    }

    public BasePager getbasePager() {
        BasePager basePager = pagrList.get(currentPosition);
        if(basePager != null && !(basePager.isCreadView)){
            basePager.isCreadView = true;
            basePager.initDate();
        }
        return basePager;
    }
}
