package com.example.hanseohelper_muso

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request.*


class request : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //팝업액티비티의 제목을 제거한다.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.5f
        window.attributes = layoutParams
        setContentView(com.example.hanseohelper_muso.R.layout.activity_request)

        //확인버튼 이벤트
        val button_ok: Button = findViewById<View>(com.example.hanseohelper_muso.R.id.btn1) as Button
        button_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //데이터 전달하고 액티비티 닫기
                val service = data_service.toString()
                val theme = data_theme.text.toString()
                val pay_method = data_pay_method.toString()
                val address = data_address.text.toString()
                val gender = data_gender.toString()
                val pay_bill = data_pay_bill.text.toString()
                val request_message = data_request_message.text.toString()
                val intent = Intent()
                intent.putExtra("service", service)
                intent.putExtra("theme", theme)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    //바깥영역 클릭 방지와 백 버튼 차단
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (event.action == MotionEvent.ACTION_OUTSIDE) {
            false
        } else true
    } //    @Override
    //    public void onBackPressed() {
    //        return;
    //    }
}