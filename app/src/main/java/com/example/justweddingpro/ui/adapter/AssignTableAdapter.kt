package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.R

class AssignTableAdapter(
    var IsVisible: Boolean,
    var mcontext: Context,
    var mList: ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>
) :
    RecyclerView.Adapter<AssignTableAdapter.ViewHolder>() {

    lateinit var onclickListner: OnItemClickListener

    interface OnItemClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnItemClickListener(onclickListner: OnItemClickListener) {
        this.onclickListner = onclickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_assign_tablelist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (IsVisible) {
            holder.ivDelete.visibility = View.VISIBLE
        } else {
            holder.ivDelete.visibility = View.GONE
        }
        holder.tvUserName.text = mList[position].getTableName()
        holder.ivDelete.setOnClickListener {
            onclickListner.onButtonClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView
        var rlMain: RelativeLayout
        var ivDelete: ImageView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            rlMain = itemView.findViewById(R.id.rlMain)
            ivDelete = itemView.findViewById(R.id.ivDelete)
        }
    }
}