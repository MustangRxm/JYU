package com.stu.app.jyu.view.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.Adapter.RecyclerViewAdapter;
import com.stu.app.jyu.Domain.AppItem;
import com.stu.app.jyu.R;

import java.util.ArrayList;
import java.util.List;


/** home page
 * A simple {@link Fragment} subclass.
 * 1.LOL开黑√
 * 2.下课聊，吹水√ bmob   *在右侧滑菜单实现聊天室？ * 右侧好友列表 bmob
 * 3.外卖？
 * 4.失物认领√
 * 5.活动√
 * 6.吹水聊天室√ bmob
 * 7.计步器
 * 8.新生导航√ baidu sdk
 * 9.技能树√
 *10.轻松10分钟√
 * 观光--baidu sdk 旅游√ baidu sdk
 * 登录方正
 * 计步器
 *图书馆
 * 树洞√
 * 跳蚤市场√
 *
 *
 */
public class SchoolLiveApplication extends Fragment {
private RecyclerView rv_sch_live_app;
private Fragment content_fragment;
    private RecyclerViewAdapter adapter;
    private List<AppItem> mAppItemList =null;
    public SchoolLiveApplication() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_school_live_application,container, false);
        rv_sch_live_app = (RecyclerView) view.findViewById(R.id.rv_sch_live_app);
//        rv_sch_live_app.addItemDecoration(new  DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        rv_sch_live_app.setLayoutManager(new GridLayoutManager(getContext(),4));
        mAppItemList = new ArrayList<>();
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_game,"Game"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chat,"吹水地"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_lostfind,"失物认领"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_activity_outline,"线下活动"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_trip,"旅游"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_new_stu_nav,"新生导航"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chattree,"树洞"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_market,"跳蚤市场"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_library,"图书馆"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_sk_tree,"技能树"));
        adapter = new RecyclerViewAdapter(getContext(),mAppItemList,R.layout.sch_live_app_recycleview_item);
//        rv_sch_live_app.setAnimation(new DefaultItemAnimator());
        rv_sch_live_app.setAdapter(adapter);

        return  view;
    }

}
