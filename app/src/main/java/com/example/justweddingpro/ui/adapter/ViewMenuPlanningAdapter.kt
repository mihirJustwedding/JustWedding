package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.EvenMenuPlanningDetails
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ViewMenuPlanningAdapter(
    var mcontext: Context,
    var mList: ArrayList<EvenMenuPlanningDetails.EventMenuPlanDetail>
) :
    RecyclerView.Adapter<ViewMenuPlanningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_viewmenu_planning, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCategoryName.text =
            "${mList[position].menuName} (${mList[position].itemsDetails?.size})"

        val mLableAdapter = LableAdapter(
            mcontext,
            mList[position].itemsDetails as ArrayList<EvenMenuPlanningDetails.ItemsDetail>
        )

        val layoutManager = FlexboxLayoutManager(mcontext)
        layoutManager.flexWrap = FlexWrap.WRAP
        holder.rvlableView.layoutManager = layoutManager
        holder.rvlableView.adapter = mLableAdapter
        holder.rvlableView.setHasFixedSize(true)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCategoryName: TextView
        var rvlableView: RecyclerView

        init {
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName)
            rvlableView = itemView.findViewById(R.id.rvlableView)
        }
    }
}