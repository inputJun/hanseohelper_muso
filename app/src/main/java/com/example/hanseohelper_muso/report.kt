package com.example.hanseohelper_muso

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_report.*

class report : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        ArrayAdapter.createFromResource(
            this,
            R.array.report_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        val spinner = findViewById<View>(R.id.spinner) as Spinner
        val text = spinner.selectedItem.toString()

        btn_report.setOnClickListener {

            if(text=="신고사유를 선택해주세요")
            {
                Toast.makeText(this, "신고사유를 선택해주세요", Toast.LENGTH_LONG).show()
            }

            else {

                val intent_id_report: String? = intent.extras?.getString("ID")
                if (intent_id_report != null) {

                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener { // 딱 한번만 갱신
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            val valuenumber =
                                intent_id_report.let { dataSnapshot.child("Account").child(it).child("Report").getValue().toString().toInt()} +1
                            databaseReference.child("Account").child(intent_id_report).child("Report").setValue(valuenumber)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                        }
                    })

                }

                databaseReference.child("Report").child(text).setValue(intent_id_report)
                Toast.makeText(this, "신고가 접수되었습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        btn_back.setOnClickListener {

            finish()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}