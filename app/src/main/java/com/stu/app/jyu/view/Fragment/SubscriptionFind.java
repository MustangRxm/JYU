package com.stu.app.jyu.view.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionFind extends Fragment {


    public SubscriptionFind() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_find, container, false);
    }

}
