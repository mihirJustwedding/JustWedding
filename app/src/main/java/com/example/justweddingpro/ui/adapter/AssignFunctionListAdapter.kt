package com.example.justweddingpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AssignFunctionResponse
import com.example.justweddingpro.utils.CommonUtils

class AssignFunctionListAdapter(
    var mcontext: Context,
    var mList: List<AssignFunctionResponse.FunctionManagerAssignDetail>
) :
    RecyclerView.Adapter<AssignFunctionListAdapter.ViewHolder>() {
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
            LayoutInflater.from(mcontext)
                .inflate(R.layout.item_assignfunction_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFunctionName.text = mList[position].functionName
        holder.tvDate.text = CommonUtils.parseDateAndTimeToViewDate(mList[position].startTime)
        holder.tvStartTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].startTime)
        holder.tvEndTime.text = CommonUtils.parseDateAndTimeToViewTime(mList[position].endTime)
        holder.tvEventName.text = mList[position].eventName

        holder.itemView.setOnClickListener {
            onclickListner.onclick(position)
        }

        holder.ivDelete.setOnClickListener {
            EditeonclickListner.onclick(position)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView
        var tvFunctionName: TextView
        var tvEventName: TextView
        var tvStartTime: TextView
        var tvEndTime: TextView
        var ivDelete: ImageView

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
            tvFunctionName = itemView.findViewById(R.id.tvFunctionName)
            tvEventName = itemView.findViewById(R.id.tvEventName)
            tvStartTime = itemView.findViewById(R.id.tvStartTime)
            tvEndTime = itemView.findViewById(R.id.tvEndTime)
            ivDelete = itemView.findViewById(R.id.ivDelete)
        }
    }
}