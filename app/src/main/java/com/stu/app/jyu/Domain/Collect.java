package com.stu.app.jyu.Domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by m1 on 16-5-28.
 */

public class Collect extends BmobObject {
    public com.stu.app.jyu.Domain.JyuUser getJyuUser() {
        return JyuUser;
    }

    public void setJyuUser(com.stu.app.jyu.Domain.JyuUser jyuUser) {
        JyuUser = jyuUser;
    }

    public com.stu.app.jyu.Domain.baseAPPUI getBaseAPPUI() {
        return baseAPPUI;
    }

    public void setBaseAPPUI(com.stu.app.jyu.Domain.baseAPPUI baseAPPUI) {
        this.baseAPPUI = baseAPPUI;
    }

    private JyuUser JyuUser;
//    private String Content;
    private baseAPPUI baseAPPUI;
}
