package com.wenhaolei.m3u8.bean;

import android.os.Environment;

public class M3U8Params {
    private static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/m3u8/";
    public static String tsSavePath = basePath + "tempTs/";
    public static String mp4Savepath = basePath + "output/";
}
