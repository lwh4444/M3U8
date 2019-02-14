package com.wenhaolei.m3u8.utils;

import android.view.View;
import com.wenhaolei.m3u8.bean.M3U8Params;
import com.wenhaolei.m3u8.interfaces.DownLoadListener;
import com.wenhaolei.tools.M3U8DownloadTask;
import com.wenhaolei.tools.bean.OnDownloadListener;
import com.wenhaolei.tools.utils.MUtils;
import com.wenhaolei.tools.utils.NetSpeedUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class M3U8DownLoadManger {
    //上一秒的大小
    private static long lastLength = 0;
    private static M3U8DownloadTask downloadTask;
    private static DownLoadListener downloadListener;

    public static void setDownloadListener(DownLoadListener downloadListener) {
        M3U8DownLoadManger.downloadListener = downloadListener;
    }

    public static void onDownload(final String url, String taskId) {
        downloadListener.startDownload();
        final String saveFilePath = M3U8Params.mp4Savepath + url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".mp4";
        File file = new File(saveFilePath);
        //判断文件是否存在 ，存在就不下载了
        if (!file.exists()) {
            doDownload(url, saveFilePath, taskId);
        } else {
            downloadListener.onEnd("文件已存在");
            System.out.println("文件已存在");
        }

    }


    /**
     * 文件下载逻辑
     */
    private static void doDownload(String url, final String saveFilePath, String taskId) {
        if (downloadTask == null || !taskId.equals(downloadTask.getTaskId()))
            downloadTask = new M3U8DownloadTask(taskId);

        System.out.println("downloadURL: " + url + "   savePath:" + M3U8Params.tsSavePath);
        String dirName = System.currentTimeMillis() + "";
        downloadTask.setSaveFilePath(M3U8Params.tsSavePath + dirName + ".ts");
        downloadTask.download(url, new OnDownloadListener() {
            @Override
            public void onDownloading(long itemFileSize, int totalTs, int curTs) {
                System.out.println(downloadTask.getTaskId() + "下载中.....itemFileSize=" + itemFileSize + "\ttotalTs=" + totalTs + "\tcurTs=" + curTs);
                downloadListener.onDownloading(curTs + "", totalTs + "");
            }

            @Override
            public void onSuccess() {
                System.out.println("下载完成");
                File dir = new File(M3U8Params.tsSavePath);
                File[] files = dir.listFiles();
                List<File> fileList = new ArrayList<>();
                for (File file : files) {
                    fileList.add(file);
                }
                try {
                    MUtils.merge(fileList, saveFilePath);
                    MUtils.clearDir(new File(M3U8Params.tsSavePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                downloadListener.onEnd("下载完成");
            }

            @Override
            public void onProgress(long curLength) {
                if (curLength - lastLength > 0) {
                    final String speed = NetSpeedUtils.getInstance().displayFileSize(curLength - lastLength) + "/s";
                    System.out.println(downloadTask.getTaskId() + "speed = " + speed);
                    lastLength = curLength;
                    //run in UI
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
