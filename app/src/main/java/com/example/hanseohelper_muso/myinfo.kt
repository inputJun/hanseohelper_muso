package com.example.hanseohelper_muso

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_myinfo.*
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_start.*

class myinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //팝업액티비티의 제목을 제거한다.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.5f
        window.attributes = layoutParams
        setContentView(R.layout.activity_myinfo)

        close_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //데이터 전달하고 액티비티 닫기
                finish()
            }
        })
    }
}