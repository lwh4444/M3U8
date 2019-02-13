package com.wenhaolei.m3u8.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.wenhaolei.m3u8.bean.MovieBean
import com.wenhaolei.m3u8.R
import com.wenhaolei.m3u8.adapter.RecyclerViewVideoAdapter
import com.wenhaolei.m3u8.utils.Utils
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mList: ArrayList<MovieBean>

    private lateinit var mAdapter: RecyclerViewVideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(isNetworkConnected(this))
        println(Utils.URLList(this))
        setUI()
        delData()
    }

    private fun delData() {
        mList = ArrayList()
        val canLoadTitle = Utils.TITLEList(this).size == Utils.URLList(this).size
        for (i in 0 until Utils.URLList(this).size) {
            val beanMovieBean = MovieBean()
            beanMovieBean.url = Utils.URLList(this)[i]
            if (canLoadTitle)
                beanMovieBean.title = Utils.TITLEList(this)[i]
            beanMovieBean.thumbs = Utils.getNetVideoBitmap(Utils.URLList(this)[i])
            mList.add(beanMovieBean)
        }
        mAdapter = RecyclerViewVideoAdapter(this, mList)
        movie_list.adapter = mAdapter
    }

    private fun setUI() {
        movie_list.layoutManager = LinearLayoutManager(this)
        movie_list.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(p0: View) {
            }

            override fun onChildViewAttachedToWindow(p0: View) {
            }
        })
        add_new_btn.setOnClickListener {
            startActivityForResult(Intent(this, AddNewURLActivity::class.java), 11)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (JCVideoPlayer.backPress()) {
            return
        }
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            delData()
        }
    }
}