package com.stu.app.jyu.view.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyu.Adapter.sch_news_App_RecyclerViewAdapter;
import com.stu.app.jyu.Domain.JYU_Important_News;
import com.stu.app.jyu.R;
import com.stu.app.jyu.Utils.NewsUtils;
import com.stu.app.jyu.Utils.TimeUtils;
import com.stu.app.jyu.view.Activity.News_Entity_Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 底部使用导航栏,分3部分,直接爬综合要闻,校园公告,校园动态,如果设置了学院,再加个学院动态要闻
 * A simple {@link Fragment} subclass.
 */
public class SchoolNews extends Fragment {
    private sch_news_App_RecyclerViewAdapter sch_news_Rv_Adapter;
    private List<JYU_Important_News> list = new ArrayList<JYU_Important_News>();
    private Context mcontext;
    private LinearLayoutManager linearLayoutManager;
    private String year_month;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (sch_news_Rv_Adapter == null) {
                        sch_news_Rv_Adapter = new sch_news_App_RecyclerViewAdapter(getContext(), list, R.layout.sch_news_cardview_item);
//                        sch_news_Rv_Adapter.setheadView(View.inflate(getContext(),R.layout.testhead,null));
                        sch_news_Rv_Adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getContext(), News_Entity_Activity.class);
                                EventBus.getDefault().postSticky(list.get(position));
                                startActivity(intent);
                            }
                        });
                        rv_sch_news_app.setAdapter(sch_news_Rv_Adapter);
                        Log.i("test20160601","list size::"+list.size());
                    } else {
                        sch_news_Rv_Adapter.notifyDataSetChanged();
                        srl_sch_news_app.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    public SchoolNews() {
    }

    List<JYU_Important_News> list_sources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mcontext = getContext();
        year_month = TimeUtils.getServerTime(mcontext, "yy-MM");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_school_news, container, false);
        bindView(view);
        initData();
        initView();
        initEvent();
        return view;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverListData(List<JYU_Important_News> list) {
        this.list.addAll(list);
        //要注意，目前这样的请求数据方式是有问题的，后续想办法跟进
        Log.i("test20160601","enter fragment eventbus");
        Log.i("test20160601","enter fragment eventbus,send list size:"+list.size());
        if (list.size()==0){
            new AsyncTask<Object, Float, Object>() {
                @Override
                protected Object doInBackground(Object[] params) {
                    String[] times = year_month.split("-");
                    int year = Integer.parseInt(times[0]);
                    int mon = Integer.parseInt(times[1]);
                    if (mon > 1) {
                        mon = mon - 1;
                    } else if (mon == 1) {
                        if (year == 0) {
                            year = 99;
                        } else {
                            year = year - 1;
                        }
                        mon = 12;
                    }
                    if (mon < 10) {
                        year_month = year + "-0" + mon;
                    } else {
                        year_month = year + "-" + mon;
                    }
                    NewsUtils.getNewsData(getContext(), year_month);
                    return null;
                }
            }.execute();
        }
        mHandler.sendEmptyMessage(1);
        //如果splashActivity 在没有网络的时候，是进不了onsuccess，所以这里根本无法接收，自然不会有初始化adapter的操作
    }


    private int lastVisibleItem;

    private void initEvent() {
        srl_sch_news_app.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_sch_news_app.setRefreshing(false);
            }

        });
        rv_sch_news_app.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE && lastVisibleItem + 1 == sch_news_Rv_Adapter.getItemCount()) {
                    //update list
                    srl_sch_news_app.setRefreshing(true);
                    new AsyncTask<Object, Float, Object>() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            String[] times = year_month.split("-");
                            int year = Integer.parseInt(times[0]);
                            int mon = Integer.parseInt(times[1]);
                            if (mon > 1) {
                                mon = mon - 1;
                            } else if (mon == 1) {
                                if (year == 0) {
                                    year = 99;
                                } else {
                                    year = year - 1;
                                }
                                mon = 12;
                            }
                            if (mon < 10) {
                                year_month = year + "-0" + mon;
                            } else {
                                year_month = year + "-" + mon;
                            }
                            NewsUtils.getNewsData(getContext(), year_month);
                            return null;
                        }
                    }.execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_sch_news_app.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
    }

    private RecyclerView rv_sch_news_app;
//    private SimpleDraweeView mSimpleDraweeView;
    private SwipeRefreshLayout srl_sch_news_app;

    private void bindView(View view) {
        rv_sch_news_app = (RecyclerView) view.findViewById(R.id.rv_sch_news_app);
        srl_sch_news_app = (SwipeRefreshLayout) view.findViewById(R.id.srl_sch_news_app);
//                mSimpleDraweeView= (SimpleDraweeView) view.findViewById(R.id.sv_sch_news_cardview_item);

    }


}
