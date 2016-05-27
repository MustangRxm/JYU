package com.stu.app.jyu.view.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.R;

/**
 * @author Jack
 * @time 2016/5/27 0027 15:03
 * @des TODO
 */

public class ScreenSlidePageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        return view;
    }
}
