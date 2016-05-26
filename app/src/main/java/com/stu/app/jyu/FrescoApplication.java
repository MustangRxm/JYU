package com.stu.app.jyu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.stu.app.jyu.Utils.ImagePipelineConfigFactory;

import cn.bmob.v3.Bmob;

/**
 * Created by 06peng on 2015/6/24.
 */
public class FrescoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"06beaae856eb317097fd9381493b62ed");
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }
}
