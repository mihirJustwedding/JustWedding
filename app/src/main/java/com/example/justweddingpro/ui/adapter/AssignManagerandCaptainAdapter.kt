package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.Response.AssignManagerAndCaptainResponse

class AssignManagerandCaptainAdapter(
    var mcontext: Context,
    var mList: List<AssignManagerAndCaptainResponse.FunctionManagerAssignDetail>
) : RecyclerView.Adapter<AssignManagerandCaptainAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner


    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int, mDetail: AssignManagerAndCaptainResponse.Detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view = LayoutInflater.from(mcontext)
            .inflate(R.layout.item_assign_managerand_captain_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = mList[position].name

        val linearLayoutManager = LinearLayoutManager(
            mcontext, RecyclerView.VERTICAL, false
        )
        val mItemAdapter = AssignListAdapter(
            mcontext, mList[position].details!!
        )
        holder.rvList.layoutManager = linearLayoutManager
        holder.rvList.adapter = mItemAdapter

        mItemAdapter.SetOnclickListner(object : AssignListAdapter.OnclickListner {
            override fun onclick(
                positionChild: Int,
                mDetail: AssignManagerAndCaptainResponse.Detail
            ) {
                onclickListner.onclick(positionChild, mDetail)
            }
        })
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var rvList: RecyclerView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            rvList = itemView.findViewById(R.id.rvList)
        }
    }
}