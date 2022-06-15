package com.deepika.mentionedittext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val mContext: Context,val list: ArrayList<String>):RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textView=itemView.findViewById<TextView>(R.id.tvItemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.mention_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.textView.text=list[position]
    }

    override fun getItemCount(): Int {
       return list.size
    }
}