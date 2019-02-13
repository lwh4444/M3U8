package com.wenhaolei.m3u8.utils;

import com.wenhaolei.m3u8.bean.M3U8Params;
import com.wenhaolei.m3u8.interfaces.DownLoadListener;
import com.wenhaolei.tools.M3U8DownloadTask;
import com.wenhaolei.tools.bean.OnDownloadListener;
import com.wenhaolei.tools.utils.NetSpeedUtils;

public class M3U8DownLoadManger {
    //上一秒的大小
    private static long lastLength = 0;
    private static M3U8DownloadTask downloadTask;
    private static DownLoadListener downloadListener;

    public void setDownloadListener(DownLoadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public static void onDownload(String url) {
        if (downloadTask == null)
            downloadTask = new M3U8DownloadTask("1001");

        downloadTask.setSaveFilePath(M3U8Params.tsSavePath);
        downloadTask.download(url, new OnDownloadListener() {
            @Override
            public void onDownloading(long itemFileSize, int totalTs, int curTs) {
                System.out.println(downloadTask.getTaskId() + "下载中.....itemFileSize=" + itemFileSize + "\ttotalTs=" + totalTs + "\tcurTs=" + curTs);
                downloadListener.onDownloading(curTs + "", totalTs + "");
            }

            @Override
            public void onSuccess() {
                System.out.println("下载完成");
                downloadListener.onEnd();

            }

            @Override
            public void onProgress(long curLength) {
                if (curLength - lastLength > 0) {
                    final String speed = NetSpeedUtils.getInstance().displayFileSize(curLength - lastLength) + "/s";
                    System.out.println(downloadTask.getTaskId() + "speed = " + speed);
                    lastLength = curLength;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("更新了");
////                            tvSpeed1.setText(speed);
//                            System.out.println(speed);
//                        }
//                    });

                }

            }

            @Override
            public void onStart() {
                System.out.println("开始下载");

            }

            @Override
            public void onError(Throwable errorMsg) {
                System.out.println("error：" + errorMsg);

            }
        });
    }
}
