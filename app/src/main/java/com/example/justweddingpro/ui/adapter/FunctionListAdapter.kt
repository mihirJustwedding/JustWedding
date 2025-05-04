package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.Network.RequestModel.EventFunctionDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.utils.CommonUtils

class FunctionListAdapter(
    var mcontext: Context,
    var mList: List<EventFunctionDetail>
) :
    RecyclerView.Adapter<FunctionListAdapter.ViewHolder>() {
    lateinit var onclickListner: OnclickListner
    lateinit var EditeonclickListner: OnclickListner

    fun SetOnEditeclickListner(monclickListner: OnclickListner) {
        this.EditeonclickListner = monclickListner
    }

    fun SetOnclickListner(monclickListner: OnclickListner) {
        this.onclickListner = monclickListner
    }

    interface OnclickListner {
        fun onclick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        view =
            LayoutInflater.from(mcontext).inflate(R.layout.item_addfunction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFunctionName.text = mList[position].functionName
        holder.tvDate.text = CommonUtils.parseDateAndTimeToViewDate(mList[position].starttime)
        holder.tvStartTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].starttime)
        holder.tvEndTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].endtime)
        holder.tvVanueName.text = mList[position].vanueName
        holder.tvPax.text = mList[position].pax.toString()

        holder.ivDelete.setOnClickListener {
            onclickListner.onclick(position)
        }

        holder.imgEdite.setOnClickListener {
            EditeonclickListner.onclick(position)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView
        var tvFunctionName: TextView
        var tvVanueName: TextView
        var tvStartTime: TextView
        var tvEndTime: TextView
        var tvPax: TextView
        var ivDelete: ImageView
        var imgEdite: ImageView

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
            tvFunctionName = itemView.findViewById(R.id.tvFunctionName)
            tvVanueName = itemView.findViewById(R.id.tvVanueName)
            tvStartTime = itemView.findViewById(R.id.tvStartTime)
            tvEndTime = itemView.findViewById(R.id.tvEndTime)
            tvPax = itemView.findViewById(R.id.tvPax)
            ivDelete = itemView.findViewById(R.id.ivDelete)
            imgEdite = itemView.findViewById(R.id.imgEdite)
        }
    }
}