package com.stu.app.jyu.Utils;

import android.content.Context;
import android.util.Log;

import com.stu.app.jyu.Domain.JYU_Important_News;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:29
 * @des TODO
 */

public class NewsUtils {
    public static void getNewsData(final Context mcontext, final String Year_month) {

        //先从网络加载数据,
        if (NetWorkUtils.isOpenNetWork(mcontext)) {

            Log.i("20160601", "enter network loader");
            BmobQuery<JYU_Important_News> query_News = new BmobQuery<JYU_Important_News>();
            query_News.addWhereContains("Date", Year_month + "-");
            query_News.order("-Date");//字符前面有个-,就是降序,否则默认字符就是升序
            query_News.findObjects(mcontext, new FindListener<JYU_Important_News>() {
                @Override
                public void onSuccess(List<JYU_Important_News> mlist) {
                    Log.i("20160601", "enter network loader mlist size::"+mlist.size());
                    CacheUtils cacheUtils = new CacheUtils(mcontext);
                    cacheUtils.saveJsonToCacheFile(mlist, Year_month);
                    EventBus.getDefault().postSticky(mlist);
                    cacheUtils = null;
                }

                @Override
                public void onError(int i, String s) {
                    //                CacheUtils cacheUtils = new CacheUtils(mcontext);
                    //                List<JYU_Important_News> list  = cacheUtils.getJsonStr();
                    //                EventBus.getDefault().postSticky(list);
                    //弹提示框，请检查网络
                }
            });

        } else {
            Log.i("20160601", "enter no internet loader");
            //如果没有，去硬盘
            CacheUtils cacheUtils = new CacheUtils(mcontext);
            //        final String year =TimeUtils.getServerTime(mcontext,"yy");
            List<JYU_Important_News> list = cacheUtils.getJsonStr(Year_month);
            if (list != null) {
                EventBus.getDefault().postSticky(list);
                cacheUtils = null;
                list = null;
                //        return;
            }
        }

    }

}
