package com.xinzeyijia.houselocks.util.sdutil;

import android.os.Environment;

import com.xinzeyijia.houselocks.App;
import com.xinzeyijia.houselocks.R;

import java.io.File;

/**
 * Created by ATt on 2016/11/22.
 */


public interface Path {
    String DEFAULT_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 资源默认sd存储路径
     */
    String FOLDER_BASE_PATH = "xinze";
    String DEFAULT_FOLDER_PATH = DEFAULT_ROOT + "/" + FOLDER_BASE_PATH;

    String DEFAULT_IMAGE_CACHE_PATH = DEFAULT_FOLDER_PATH + "/ImgCache";
    /**
     * 图片存放路径
     */
    String TAOYUAN_COMPRESS_SAVE = new File(Environment.getExternalStorageDirectory(), App.getAppContext().getString(R.string.app_name) + "保存的文件").getPath();
    /**
     * `
     * 记录通知文件
     */
    String DEFAULT_FILE_CACHE_PATH = DEFAULT_FOLDER_PATH + "/file";

    /**
     * 头像存放路径
     */
    String HEAD_FILE_PATH = DEFAULT_FOLDER_PATH + "/img/";
    /**
     * 异常存放路径
     */
    String SD_CARD_EXCEPTION_PATH = DEFAULT_FOLDER_PATH + "/exception/";
    /**
     * 日志存放路径
     */
    String SD_CARD_LOG_PATH = DEFAULT_FOLDER_PATH + "/log/";
    /**
     * 文件名
     */
    String FILE_NAME = "crash";
    /**
     * log文件名
     */
    String LOG_NAME = "log";

    /**
     * 文件名后缀
     */
    String FILE_NAME_SUFFIX = ".txt";

}
