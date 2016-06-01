package com.stu.app.jyu.Domain;


import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by m1 on 16-5-28.
 */

public  class baseAPPUI {
    private Integer TYPE;
    baseAPPUI(Integer TYPE){
        this.TYPE = TYPE;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BmobDate getDate() {
        return Date;
    }

    public void setDate(BmobDate date) {
        Date = date;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

//    public com.stu.app.jyu.Domain.Comment getComment() {
//        return Comment;
//    }
//
//    public void setComment(com.stu.app.jyu.Domain.Comment comment) {
//        Comment = comment;
//    }
//
//    public com.stu.app.jyu.Domain.Collect getCollect() {
//        return Collect;
//    }
//
//    public void setCollect(com.stu.app.jyu.Domain.Collect collect) {
//        Collect = collect;
//    }

    public com.stu.app.jyu.Domain.JyuUser getJyuUser() {
        return JyuUser;
    }

    public void setJyuUser(com.stu.app.jyu.Domain.JyuUser jyuUser) {
        JyuUser = jyuUser;
    }

    private List<String> imageUrl;
    private String location;
    private BmobDate Date;
    private String Contact;
//    private Comment Comment;
    //    private Shared  Shared;
//    private Collect Collect;
    private JyuUser JyuUser;


}
