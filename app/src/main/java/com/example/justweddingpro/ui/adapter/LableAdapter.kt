package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.EvenMenuPlanningDetails

class LableAdapter(
    var mcontext: Context,
    var mList: ArrayList<EvenMenuPlanningDetails.ItemsDetail>
) :
    RecyclerView.Adapter<LableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_lable_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvLableName.text = mList[position].itemName
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLableName: TextView

        init {
            tvLableName = itemView.findViewById(R.id.tvLableName)
        }
    }
}