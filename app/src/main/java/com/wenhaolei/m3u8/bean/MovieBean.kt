package com.wenhaolei.m3u8.bean

import android.graphics.Bitmap

data class MovieBean(
    var url: String? = "",
    var addTime: String? = "",
    var title: String? = "",
    var thumbs: Bitmap? = null
)