package com.example.hanseohelper_muso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent // intent를 사용하려면 넣어야 함
import kotlinx.android.synthetic.main.activity_login.*  // 자동으로 추가 됨


class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)

        }
    }
}