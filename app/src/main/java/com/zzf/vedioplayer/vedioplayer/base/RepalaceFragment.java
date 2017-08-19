package com.zzf.vedioplayer.vedioplayer.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzf.vedioplayer.vedioplayer.base.BasePager;

/**
 * Created by zzf on 2017/8/17.
 */

public class RepalaceFragment extends Fragment {

        private BasePager currPager;

        public RepalaceFragment(BasePager pager) {
            this.currPager=pager;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (currPager != null) {
                return currPager.rootView;
            }
            return null;
        }
}
