package com.example.hanseohelper_muso

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_myclient.*

class myclient : AppCompatActivity() {


    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myclient)

        val intent_id_myclient: String? = intent.extras?.getString("ID")

        bt_report_myclient.setOnClickListener {
            val intent = Intent(this, report::class.java)
            intent.putExtra("ID",intent_id_myclient)
            startActivity(intent)
        }


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                // 의뢰 이름
                val requestname_myinfo =
                    intent_id_myclient?.let { dataSnapshot.child("Account").child(it).child("Request").getValue().toString() }
                title_myclient.setText(requestname_myinfo)

                // 의뢰자 이름
                val name =
                    intent_id_myclient?.let { dataSnapshot.child("Account").child(it).child("Name").getValue().toString() }
                name_myclient.setText(name)

                // 의뢰자 학번
                val studentnumber =
                    requestname_myinfo?.let { dataSnapshot.child("Request").child(it).child("reqWho").getValue().toString() }
                studentnumber_myclient.setText(studentnumber)

                // 의뢰 서비스 구분
                val service =
                    requestname_myinfo?.let { dataSnapshot.child("Request").child(it).child("reqCategory").getValue().toString() }
                service_myclient.setText(service)

                // 의뢰비
                val pay_bill =
                    requestname_myinfo?.let { dataSnapshot.child("Request").child(it).child("reqPay").getValue().toString() }
                val pay_bill2 = pay_bill + " 원 "
                pay_bill_myclient.setText(pay_bill2)

                // 의뢰 지불 방식
                val pay_method =
                    requestname_myinfo?.let { dataSnapshot.child("Request").child(it).child("reqPayMethod").getValue().toString() }
                val pay_method2 = "(" + pay_method + ")"
                pay_method_myclient.setText(pay_method2)

                // 의뢰 요청사항
                val request_message =
                    requestname_myinfo?.let { dataSnapshot.child("Request").child(it).child("reqMessage").getValue().toString() }
                val request_message2 = " - " + request_message
                request_message_myclient.setText(request_message2)

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