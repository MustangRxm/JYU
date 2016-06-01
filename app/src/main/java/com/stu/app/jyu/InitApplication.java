package com.stu.app.jyu;

import android.app.Application;
import android.graphics.Color;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.stu.app.jyu.ImageLoader.GlideImageLoader;
import com.stu.app.jyu.Utils.ImagePipelineConfigFactory;

import cn.bmob.v3.Bmob;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by 06peng on 2015/6/24.
 *
 *
 *
 */
public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "06beaae856eb317097fd9381493b62ed");
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
       ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.WHITE)
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(9)

                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setEnablePreview(true)
                .build();
        //        //配置imageloader
//        PicassoImageLoader imageLoader = new PicassoImageLoader();
        GlideImageLoader imageLoader = new GlideImageLoader();
        //        ImageLoader imageloader = new UILImageLoader();
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, theme)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(new P(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }
}
