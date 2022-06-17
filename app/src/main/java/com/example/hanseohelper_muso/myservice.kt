package com.example.hanseohelper_muso

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_myservice.*

class myservice : AppCompatActivity() {


    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myservice)

        val intent_id_myservice : String? = intent.extras?.getString("ID")

        bt_report_myservice.setOnClickListener {
            val intent = Intent(this, report::class.java)
            intent.putExtra("ID",intent_id_myservice)
            startActivity(intent)
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                // 의뢰 이름
                val requestname =
                    intent_id_myservice?.let { dataSnapshot.child("Account").child(it).child("Request").getValue().toString() }
                title_myservice.setText(requestname)

                // 의뢰자 이름
                val name =
                    intent_id_myservice?.let { dataSnapshot.child("Account").child(it).child("Name").getValue().toString() }
                name_myservice.setText(name)

                // 의뢰자 학번
                val studentnumber =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqWho").getValue().toString() }
                studentnumber_myservice.setText(studentnumber)

                // 의뢰한 곳 주소
                val address =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqAddress").getValue().toString() }
                address_myservice.setText(address)

                // 의뢰 서비스 구분
                val service =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqCategory").getValue().toString() }
                service_myservice.setText(service)

                // 의뢰비
                val pay_bill =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqPay").getValue().toString() }
                val pay_bill2 = pay_bill + " 원 "
                pay_bill_myservice.setText(pay_bill2)

                // 의뢰 지불 방식
                val pay_method =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqPayMethod").getValue().toString() }
                val pay_method2 = "(" + pay_method + ")"
                pay_method_myservice.setText(pay_method2)

                // 의뢰 요청사항
                val request_message =
                    requestname?.let { dataSnapshot.child("Request").child(it).child("reqMessage").getValue().toString() }
                val request_message2 = " - " + request_message
                request_message_myservice.setText(request_message2)

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