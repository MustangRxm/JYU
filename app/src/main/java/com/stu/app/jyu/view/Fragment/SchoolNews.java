package com.stu.app.jyu.view.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.R;

/**
 * 底部使用导航栏,分3部分,直接爬综合要闻,校园公告,校园动态,如果设置了学院,再加个学院动态要闻
 * A simple {@link Fragment} subclass.
 */
public class SchoolNews extends Fragment {


    public SchoolNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_school_news, container, false);
    }

}
