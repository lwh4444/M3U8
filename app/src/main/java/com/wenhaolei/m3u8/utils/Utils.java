package com.wenhaolei.m3u8.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Utils {
    private static String URLPATH = "url.txt";
    private static String TITLEPATH = "title.txt";


    public static ArrayList<String> URLList(Context context) {
        String url = readStringFromAssets(context, URLPATH).trim();
        ArrayList<String> mUrlList = new ArrayList<String>();
        if (url.length() > 0) {
            String[] urls = url.split(";");
            for (int i = 0; i < urls.length; i++) {
                mUrlList.add(urls[i]);
            }
        }
        return mUrlList;
    }

    public static ArrayList<String> TITLEList(Context context) {
        String url = readStringFromAssets(context, TITLEPATH).trim();
        ArrayList<String> mUrlList = new ArrayList<String>();
        if (url.length() > 0) {
            String[] urls = url.split(";");
            for (int i = 0; i < urls.length; i++) {
                mUrlList.add(urls[i]);
            }
        }
        return mUrlList;
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    private static String readStringFromAssets(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            char[] input = new char[fis.available()];  //available()用于获取filename内容的长度
            isr.read(input);  //读取并存储到input中

            isr.close();
            fis.close();//读取完成后关闭

            String str = new String(input); //将读取并存放在input中的数据转换成String输出
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean writeURL(Context context, String content) {
        String url = readStringFromAssets(context, URLPATH).trim();
        String str;
        if (url.length() > 0)
            str = url + content;
        else
            str = content;
        return writeFile(context, URLPATH, str + ";");
    }

    public static boolean writeTitle(Context context, String content) {
        String title = readStringFromAssets(context, TITLEPATH).trim();
        String str;
        if (title.length() > 0)
            str = title + content;
        else
            str = content;
        return writeFile(context, TITLEPATH, str + ";");
    }

    private static boolean writeFile(Context context, String fileName, String content) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(content);

            osw.flush();
            fos.flush();  //flush是为了输出缓冲区中所有的内容

            osw.close();
            fos.close();  //写入完成后，将两个输出关闭

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void clear(Context context) {
        writeFile(context, URLPATH, "");
        writeFile(context, TITLEPATH, "");
    }
}
