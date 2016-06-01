package com.stu.app.jyu.ViewHolder;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stu.app.jyu.R;

/**
 * @author Jack
 * @time 2016/5/30 0030 21:04
 * @des TODO
 */

public class take_photo_ViewHolder extends BaseViewHolder {
    public SimpleDraweeView mSimpleDraweeView;
    public take_photo_ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(View itemView) {
        mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_take_phone);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("fortheviewholder","testtest enter view holder");
//            }
//        });
    }
}
