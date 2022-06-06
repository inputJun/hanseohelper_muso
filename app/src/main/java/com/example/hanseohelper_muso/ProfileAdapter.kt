package com.example.hanseohelper_muso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(val profileList: ArrayList<Profiles>) : RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: ProfileAdapter.CustomViewHolder, position: Int) {
        holder.ser_pic.setImageResource(profileList.get(position).ser_pic)
        holder.ser_cat.text = profileList.get(position).ser_cat
        holder.ser_theme.text = profileList.get(position).ser_theme
        holder.ser_pay.text = profileList.get(position).ser_pay.toString() + "원"
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ser_pic = itemView.findViewById<ImageView>(R.id.iv_category) // 서비스 그림
        val ser_cat = itemView.findViewById<TextView>(R.id.tv_category)  // 서비스 카테고리
        val ser_theme = itemView.findViewById<TextView>(R.id.tv_theme)// 서비스 제목
        val ser_pay = itemView.findViewById<TextView>(R.id.tv_pay_bill)  // 서비스 비용
    }
}