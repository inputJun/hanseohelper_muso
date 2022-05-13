package com.example.hanseohelper_muso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var sign_up: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        sign_up = findViewById(R.id.btn_register3)
        btn_register3.setOnClickListener(View.OnClickListener { signUp() })
    }

    // When initializing your Activity, check to see if the user is currently signed in.
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
    }

    private fun signUp() {
        // 이메일
        val emailEditText = findViewById<EditText>(R.id.edit_email)
        val emailsub = emailEditText.text.toString()
        val domain = "@naver.com"
        val email = emailsub+domain

        // 비밀번호
        val passwordEditText = findViewById<EditText>(R.id.edit_pw)
        val password = passwordEditText.text.toString()

        mAuth!!.createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth!!.currentUser

                        sendEmailVerification()

                        val intent = Intent(this@register, login::class.java)
                        startActivity(intent)
                        Toast.makeText(this@register, "등록 완료", Toast.LENGTH_SHORT).show()
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@register, "등록 에러", Toast.LENGTH_SHORT).show()
                        return@OnCompleteListener
                    }
                })
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }

    fun sendEmailVerification(){

        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){
            Toast.makeText(this, "이메일 인증이 이미 완료되었습니다", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "확인메일을 보냈습니다", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}

