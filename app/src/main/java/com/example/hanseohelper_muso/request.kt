package com.example.hanseohelper_muso

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_request.*


class request : AppCompatActivity() {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //팝업액티비티의 제목을 제거한다.
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.5f
        window.attributes = layoutParams
        setContentView(R.layout.activity_request)

        //확인버튼 이벤트
        val button_ok: Button = findViewById<View>(R.id.btn1) as Button
        button_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //데이터 전달하고 액티비티 닫기

                // 의뢰제목
                val theme = data_title.text.toString()
                databaseReference.child("Request").child(theme)
                databaseReference.child("Request").child(theme).child("reqTheme").setValue(theme)

                // 의뢰 서비스
                if(service1.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service1).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/post.png?alt=media&token=5dfb87fe-68bd-4b4d-b719-dd1a213ee207")

                }
                if(service2.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service2).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/clean.png?alt=media&token=e30c8c50-cd19-4eaf-88d4-0c62bee52eb8")
                }
                if(service3.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service3).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/bug.png?alt=media&token=21435887-77a6-4743-9787-27a2bc0e9e29")
                }
                if(service4.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service4).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/study.png?alt=media&token=ca758a5e-aabd-4856-b725-4df14a08bb89")
                }
                if(service5.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service5).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/pet.png?alt=media&token=ad4e8a59-09fb-49fa-9ea0-87d9ff367f48")
                }
                if(service6.isChecked) {
                    val service = findViewById<RadioButton>(R.id.service6).text.toString()
                    databaseReference.child("Request").child(theme).child("reqCategory").setValue(service)
                    databaseReference.child("Request").child(theme).child("reqImage").setValue("https://firebasestorage.googleapis.com/v0/b/hanseo-helper.appspot.com/o/etcicon.png?alt=media&token=096a6db2-801b-481c-9836-935281013dac")
                }

                // 의뢰 결제방법
                if(pay1.isChecked) {
                    val pay_method = findViewById<RadioButton>(R.id.pay1).text.toString()
                    databaseReference.child("Request").child(theme).child("reqPayMethod").setValue(pay_method)
                }
                if(pay2.isChecked) {
                    val pay_method = findViewById<RadioButton>(R.id.pay2).text.toString()
                    databaseReference.child("Request").child(theme).child("reqPayMethod").setValue(pay_method)
                }

                // 의뢰 주소
                val address = data_address.text.toString()
                databaseReference.child("Request").child(theme).child("reqAddress").setValue(address)

                // 의뢰 원하는 성별
                if(gender1.isChecked) {
                    val gender = findViewById<RadioButton>(R.id.gender1).text.toString()
                    databaseReference.child("Request").child(theme).child("reqGender").setValue(gender)
                }
                if(gender2.isChecked) {
                    val gender = findViewById<RadioButton>(R.id.gender2).text.toString()
                    databaseReference.child("Request").child(theme).child("reqGender").setValue(gender)
                }
                if(gender3.isChecked) {
                    val gender = findViewById<RadioButton>(R.id.gender3).text.toString()
                    databaseReference.child("Request").child(theme).child("reqGender").setValue(gender)
                }

                // 의뢰비
                val pay_bill = data_pay_bill.text.toString()
                databaseReference.child("Request").child(theme).child("reqPay").setValue(pay_bill)

                // 의뢰 요청사항
                val request_message = data_request_message.text.toString()
                databaseReference.child("Request").child(theme).child("reqMessage").setValue(request_message)

                // 의뢰한 사람 ID(학번) 받아오기
                val intent_id_request = intent.extras?.getString("ID")
                databaseReference.child("Request").child(theme).child("reqWho").setValue(intent_id_request)

                // account에 저장할 의뢰제목
                if (intent_id_request != null) {
                    databaseReference.child("Account").child(intent_id_request).child("Request").setValue(theme)
                }

                Toast.makeText(this@request, "의뢰 등록이 완료되었습니다.", Toast.LENGTH_LONG).show()

                val intent = Intent(this@request, main::class.java)
                startActivity(intent)

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