package com.stu.app.jyu.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.stu.app.jyu.Domain.JYU_Important_News;
import com.stu.app.jyu.R;
import com.stu.app.jyu.ViewHolder.BaseViewHolder;
import com.stu.app.jyu.ViewHolder.sch_news_ViewHolder;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/18 0018 15:25
 * @des TODO
 */
public class sch_news_App_RecyclerViewAdapter extends BaseRecyclerViewAdapter {
    List<JYU_Important_News> list_sourse;
    private Context mContext;

    public sch_news_App_RecyclerViewAdapter(Context context, List<JYU_Important_News> list, int resource) {
        super(context, list, resource);
        list_sourse = list;
        this.mContext = context;
    }

    @Override
    protected BaseViewHolder getViewHolder(View view) {
        return new sch_news_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(BaseViewHolder holder, int position) {
        JYU_Important_News item = list_sourse.get(position);
        holder.getView().setTag(position);
        List<String> list_img = item.getNews_Img();
        sch_news_ViewHolder mholder = (sch_news_ViewHolder) holder;
        mholder.setObj(item);
        if (list_img.size() == 0) {
            mholder.sv_sch_news_cardview_item.setImageURI(Uri.parse("res:///" + R.mipmap.ic_launcher));
        } else {
            mholder.sv_sch_news_cardview_item.setImageURI(Uri.parse(list_img.get(0)));
        }
        String[] Titles = item.getTitle().split(" ");
        mholder.tv_sch_news_title_cardview_item.setText(Titles[0]);
        mholder.tv_sch_news_date_cardview_item.setText(item.getDate());
    }

}
