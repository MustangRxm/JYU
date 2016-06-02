package com.stu.app.jyu.Utils;

import java.io.File;

/**
 * @author Jack
 * @time 2016/6/2 0002 13:56
 * @des TODO
 */

public class FileUtils {
    //递归删除文件及文件夹
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }
}
