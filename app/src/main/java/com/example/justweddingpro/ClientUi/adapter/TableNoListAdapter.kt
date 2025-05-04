package com.example.justweddingpro.ClientUi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.R

class TableNoListAdapter(var mcontext: Context, var mList: ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>) :
    RecyclerView.Adapter<TableNoListAdapter.ViewHolder>() {

    lateinit var onclickListner: OnclickListner

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(ItemId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_tableno_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextview.text = mList[position].getTableName()
        holder.itemView.setOnClickListener {
            onclickListner.onclick(mList[position].getTableId().toString())
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextview: TextView

        init {
            mTextview = itemView.findViewById(R.id.mTextview)
        }
    }
}