package com.example.hanseohelper_muso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

import android.content.Intent // intent를 사용하려면 넣어야 함
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*  // 자동으로 추가 됨


class login : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val emailEditText = findViewById<EditText>(R.id.edit_email_register)
            val emailsub = emailEditText.text.toString()
            val domain = "@hanseo.ac.kr"
            val email = emailsub + domain

            val passwordEditText = findViewById<EditText>(R.id.edit_pw_register)
            val password = passwordEditText.text.toString()

            signIn(email, password)
        }
    }

    fun signIn(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if(task.isSuccessful && user!!.isEmailVerified) {
                        Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_LONG).show()
                        moveMainPage(task.result?.user)
                    }

                    else if(task.isSuccessful) {
                        Toast.makeText(this, "이메일 인증을 진행해주세요.", Toast.LENGTH_LONG).show()
                    }

                    else {
                        Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, main::class.java))
        }
    }
}