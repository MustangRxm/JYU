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
public class Subscription extends Fragment {


    public Subscription() {
        // Required empty public constructor
    }

//    private LinearLayoutManager linearLayoutManager;
//
//    private void initView() {
//
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        rv_subscription_app.setLayoutManager(linearLayoutManager);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
//        bindView(view);
//
//        initView();
        return view;
    }
//private RecyclerView rv_subscription_app;
//    private void bindView(View view) {
//        rv_subscription_app = (RecyclerView) view.findViewById(R.id.rv_subscription_app);
//    }


}
