package com.wenhaolei.m3u8.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenhaolei.m3u8.R
import com.wenhaolei.m3u8.bean.MovieBean
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

class RecyclerViewVideoAdapter(private val context: Context, private var videoList: ArrayList<MovieBean>) :
    RecyclerView.Adapter<RecyclerViewVideoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videoview, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.jcVideoPlayer.setUp(
            videoList[position].url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
            videoList[position].title.orEmpty()
        )
        if (videoList[position].thumbs != null) {
            holder.jcVideoPlayer.thumbImageView.setImageBitmap(videoList[position].thumbs)
        } else {
            holder.jcVideoPlayer.thumbImageView.setBackgroundResource(R.mipmap.ic_launcher)
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jcVideoPlayer: JCVideoPlayerStandard =
            itemView.findViewById<View>(R.id.videoplayer) as JCVideoPlayerStandard

    }

    companion object {
        const val TAG = "RecyclerViewVideoAdapter"
    }

}
