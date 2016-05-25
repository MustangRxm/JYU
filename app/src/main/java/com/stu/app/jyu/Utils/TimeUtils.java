package com.stu.app.jyu.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:50
 * @des TODO
 */

public class TimeUtils {
    public static String getServerTime(Context context, final String format){
         String times = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
       times = formatter.format(new Date(System.currentTimeMillis()));
//        Bmob.getServerTime(context, new GetServerTimeListener() {
//            @Override
//            public void onSuccess(long l) {
//                SimpleDateFormat formatter = new SimpleDateFormat(format);
//
//                times[0] = formatter.format(new Date(l*1000l));
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });

        return times;
    }
}
