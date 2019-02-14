package com.wenhaolei.m3u8.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView
import com.wenhaolei.m3u8.R
import com.wenhaolei.m3u8.interfaces.DownLoadListener
import com.wenhaolei.m3u8.utils.M3U8DownLoadManger
import kotlinx.android.synthetic.main.activity_download_layout.*

class DownloadingActivity : AppCompatActivity(), DownLoadListener {
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_layout)
        M3U8DownLoadManger.setDownloadListener(this)
        M3U8DownLoadManger.onDownload(intent.getStringExtra("url"), "1001")
    }

    override fun startDownload() {
        text = TextView(this)
        val layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        text.layoutParams = layoutParams
        download_layout.addView(text)
    }

    override fun onDownloading(curTs: String?, totalTs: String?) {
        runOnUiThread {
            text.text = "$curTs / $totalTs"
        }
    }

    override fun onEnd() {
        text.text = "finish"
    }
}