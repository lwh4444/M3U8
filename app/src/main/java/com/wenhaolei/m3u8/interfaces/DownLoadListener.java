package com.wenhaolei.m3u8.interfaces;

public interface DownLoadListener {
    void startDownload();

//    void onProgress();

    void onDownloading(String curTs, String totalTs);

    void onEnd(String tips);
}
