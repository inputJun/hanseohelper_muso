package com.example.hanseohelper_muso

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_myinfo.*
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_start.*

class myinfo : AppCompatActivity() {

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
        setContentView(R.layout.activity_myinfo)

        val intent_id_myinfo: String? = intent.extras?.getString("ID")

        btn_close.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //데이터 전달하고 액티비티 닫기
                finish()
            }
        })

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // 본인 이름
                val name =
                    intent_id_myinfo?.let {
                        dataSnapshot.child("Account").child(it).child("Name").getValue().toString()
                    }
                name_myinfo.setText(name)

                // 본인 학번
                val studentnumber = intent_id_myinfo
                studentnumber_myinfo.setText(studentnumber)

                // 본인 이메일
                val email = intent_id_myinfo + "@hanseo.ac.kr"
                email_myinfo.setText(email)

                // 본인 번호
                val phone =
                    intent_id_myinfo?.let {
                        dataSnapshot.child("Account").child(it).child("Phone").getValue().toString()
                    }
                phone_myinfo.setText(phone)

                // 본인 평점
                val score =
                    intent_id_myinfo?.let {
                        dataSnapshot.child("Account").child(it).child("Score").getValue().toString()
                    }
                score_myinfo.setText(score)


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }

    companion object {
        const val VALUE_ONE = "1234"
    }

}