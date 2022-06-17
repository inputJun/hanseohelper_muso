package com.example.hanseohelper_muso

import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_score.*

class score : AppCompatActivity() {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = findViewById<TextView>(R.id.score)
        val scoredata = score.toString().toDouble()

        score.setText("0점")

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            score.text = "${fl} 점"
        }

        btn_evaluate.setOnClickListener {

            val intent_id_score: String? = intent.extras?.getString("ID")
            if (intent_id_score != null) {

                databaseReference.addListenerForSingleValueEvent(object :
                    ValueEventListener { // 딱 한번만 갱신
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val scoreoriginal =
                            intent_id_score.let {
                                dataSnapshot.child("Account").child(it).child("Score").getValue()
                                    .toString().toDouble()
                            }

                        val scorenumber =
                            intent_id_score.let {
                                dataSnapshot.child("Account").child(it).child("Scorenumber")
                                    .getValue()
                                    .toString().toInt()
                            } + 1

                        val scorefinal = (scoreoriginal + scoredata) / scorenumber
                        databaseReference.child("Account").child(intent_id_score).child("Report")
                            .setValue(scorefinal)

                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })

            }
        }
    }
}