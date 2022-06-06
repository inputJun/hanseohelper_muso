package com.example.hanseohelper_muso

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

class list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val profileList = arrayListOf(
            Profiles(R.drawable.clean, "청소/집안일", "청소해주세요", 5000),
            Profiles(R.drawable.post, "택배/장보기", "해미에서 떡볶이 사와줘요", 10000),
            Profiles(R.drawable.pet, "반려동물 케어", "고양이 봐줘요", 10000),
            Profiles(R.drawable.clean, "청소/집안일", "집청소 해줘요", 5000),
            Profiles(R.drawable.bug, "벌레 잡기", "벌레 잡아줘요", 6000),
            Profiles(R.drawable.post, "택배/장보기", "장보고 와줘요", 7000),
            Profiles(R.drawable.pet, "반려동물 케어", "고양이 봐줘요", 10000)
        )

        rv_profile.adapter = ProfileAdapter(profileList)
    }
}