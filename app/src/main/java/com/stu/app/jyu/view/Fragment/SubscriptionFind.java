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
//    private LinearLayoutManager linearLayoutManager;
//
//    private void initView() {
//
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        rv_subscriptionFind_app.setLayoutManager(linearLayoutManager);
//    }
//
//    private RecyclerView rv_subscriptionFind_app;
//    private void bindView(View view) {
//        rv_subscriptionFind_app = (RecyclerView) view.findViewById(R.id.rv_subscriptionFind_app);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_subscription_find, container, false);
//        bindView(view);
//        initView();
        return view;
    }

}
