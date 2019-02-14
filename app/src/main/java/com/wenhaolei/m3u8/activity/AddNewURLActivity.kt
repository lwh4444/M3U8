package com.wenhaolei.m3u8.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.wenhaolei.m3u8.R
import com.wenhaolei.m3u8.utils.Utils
import kotlinx.android.synthetic.main.activity_add_new_movie.*

class AddNewURLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_movie)

        sure_btn.setOnClickListener {
            if (url_et.text.isNotEmpty()) {
                if (!Utils.writeURL(this, url_et.text.toString().trim())) {
                    Toast.makeText(this, "url写入失败", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (title_et.text.isNotEmpty()) {
                if (!Utils.writeTitle(this, title_et.text.toString().trim())) {
                    Toast.makeText(this, "title写入失败", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        clear_btn.setOnClickListener {
            Utils.clear(this)
            Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
        download_btn.setOnClickListener {
            val intent = Intent(this, DownloadingActivity::class.java)
            startActivity(intent)
        }


    }
}